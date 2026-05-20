package com.expo.ordering.application.port.in;

import com.expo.catalog.domain.model.product.Product;
import com.expo.catalog.domain.repository.ProductRepository;
import com.expo.ordering.domain.exception.OrderNotFoundException;
import com.expo.ordering.domain.model.order.Order;
import com.expo.ordering.domain.repository.OrderRepository;

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
