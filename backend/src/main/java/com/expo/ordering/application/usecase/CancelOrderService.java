package com.expo.ordering.application.usecase;

import com.expo.ordering.application.port.in.CancelOrderUseCase;
import com.expo.ordering.application.port.out.DomainEventPublisher;
import com.expo.ordering.domain.exception.OrderNotFoundException;
import com.expo.ordering.domain.model.order.Order;
import com.expo.ordering.domain.repository.OrderRepository;
import com.expo.shared.contracts.events.OrderCancelledEvent;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public class CancelOrderService implements CancelOrderUseCase {

    private final OrderRepository orderRepository;
    private final DomainEventPublisher eventPublisher;

    public CancelOrderService(
        OrderRepository orderRepository,
        DomainEventPublisher eventPublisher
    ) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public Order execute(UUID orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId.toString()));

        order.cancel();

        orderRepository.save(order);

        List<OrderCancelledEvent.OrderItem> items = order.getItems().stream()
                .map(item -> new OrderCancelledEvent.OrderItem(
                        UUID.fromString(item.getProduct().productId()),
                        item.getQuantity().getValue()
                ))
                .toList();

        eventPublisher.publish(new OrderCancelledEvent(order.getId().value(), items));

        order.pullDomainEvents()
                .forEach(eventPublisher::publish);

        return order;
    }
}
