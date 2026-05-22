package com.expo.ordering.application.usecase;

import com.expo.contracts.catalog.CatalogContract;
import com.expo.contracts.catalog.ProductDto;
import com.expo.ordering.application.command.OrderItemCommand;
import com.expo.ordering.application.command.PlaceOrderCommand;
import com.expo.ordering.application.port.in.PlaceOrderUseCase;
import com.expo.ordering.application.port.out.DomainEventPublisher;
import com.expo.ordering.domain.exception.InsufficientStockException;
import com.expo.ordering.domain.factory.OrderFactory;
import com.expo.ordering.domain.model.order.Order;
import com.expo.ordering.domain.model.order.ProductSnapshot;
import com.expo.ordering.domain.repository.OrderRepository;
import com.expo.ordering.domain.valueobject.CustomerType;
import com.expo.shared.contracts.events.OrderPlacedEvent;
import com.expo.shared.domain.Currency;
import com.expo.shared.domain.Money;
import com.expo.shared.domain.Quantity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class PlaceOrderService implements PlaceOrderUseCase {

    private final CatalogContract catalog;
    private final OrderRepository orderRepository;
    private final DomainEventPublisher eventPublisher;
    private final OrderFactory factory;

    public PlaceOrderService(
            CatalogContract catalog,
            OrderRepository orderRepository,
            DomainEventPublisher eventPublisher,
            OrderFactory factory
    ) {
        this.catalog = catalog;
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
        this.factory = factory;
    }

    @Override
    @Transactional
    public Order execute(PlaceOrderCommand command) {
        Order order = factory.createNew(
                command.customerId(),
                command.customerType()
        );

        for (OrderItemCommand item : command.items()) {
            ProductDto productDto = catalog.getProductById(item.productId().toString());

            if (command.customerType() == CustomerType.REGULAR && item.quantity() > productDto.getStock()) {
                throw new InsufficientStockException(productDto.getName(), item.quantity(), productDto.getStock());
            }

            ProductSnapshot snapshot = new ProductSnapshot(
                    item.productId().toString(),
                    productDto.getName(),
                    new Money(productDto.getPrice(), new Currency("USD"))
            );

            order.addItem(
                    snapshot,
                    new Quantity(item.quantity())
            );
        }

        order.place();
        order.confirm();

        orderRepository.save(order);

        List<OrderPlacedEvent.OrderItem> items = command.items().stream()
                .map(item -> new OrderPlacedEvent.OrderItem(item.productId(), item.quantity()))
                .toList();

        eventPublisher.publish(new OrderPlacedEvent(order.getId().value(), items));

        order.pullDomainEvents()
                .forEach(eventPublisher::publish);

        return order;
    }
}



