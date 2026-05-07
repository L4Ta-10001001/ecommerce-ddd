package com.expo.ddd.application.usecase;

import com.expo.ddd.application.command.OrderItemCommand;
import com.expo.ddd.application.command.PlaceOrderCommand;
import com.expo.ddd.domain.model.order.Order;
import com.expo.ddd.domain.model.product.Product;
import com.expo.ddd.domain.repository.OrderRepository;
import com.expo.ddd.domain.repository.ProductRepository;
import com.expo.ddd.domain.valueobject.CustomerId;
import com.expo.ddd.domain.valueobject.OrderId;
import com.expo.ddd.domain.valueobject.Quantity;

import java.util.UUID;

/**
 * ✅ Caso de uso: orquesta la operación sin contener lógica de negocio.
 *    La lógica de negocio vive en Order y Product (los agregados).
 * ⚠️ En Proyecto A, toda esta lógica (y más) estaría en un método de OrderService,
 *    mezclando orquestación con reglas de negocio.
 */
public class PlaceOrderUseCase {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public PlaceOrderUseCase(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order execute(PlaceOrderCommand command) {
        Order order = createEmptyOrder(command.customerId());
        addItemsToOrder(order, command);
        order.confirm();
        return orderRepository.save(order);
    }

    private Order createEmptyOrder(UUID customerId) {
        return new Order(
                OrderId.of(UUID.randomUUID()),
                CustomerId.of(customerId)
        );
    }

    private void addItemsToOrder(Order order, PlaceOrderCommand command) {
        for (OrderItemCommand itemCommand : command.items()) {
            Product product = findProductOrThrow(itemCommand.productId());
            order.addItem(product, Quantity.of(itemCommand.quantity()));
            productRepository.save(product);
        }
    }

    private Product findProductOrThrow(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
    }
}
