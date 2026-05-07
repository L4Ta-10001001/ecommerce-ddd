package com.expo.ddd.application.usecase;

import com.expo.ddd.domain.exception.OrderNotFoundException;
import com.expo.ddd.domain.model.order.Order;
import com.expo.ddd.domain.model.product.Product;
import com.expo.ddd.domain.repository.OrderRepository;
import com.expo.ddd.domain.repository.ProductRepository;

/**
 * ✅ Caso de uso de cancelación: delega la lógica de restauración de stock al agregado Order.
 *    Este servicio de aplicación es deliberadamente delgado.
 */
public class CancelOrderUseCase {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public CancelOrderUseCase(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order execute(Long orderId) {
        Order order = findOrderOrThrow(orderId);
        order.cancel();
        persistRestoredStock(order);
        return orderRepository.save(order);
    }

    private Order findOrderOrThrow(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    private void persistRestoredStock(Order order) {
        for (var item : order.getItems()) {
            productRepository.save(item.getProduct());
        }
    }
}
