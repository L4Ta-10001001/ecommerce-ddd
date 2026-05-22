package com.expo.ordering.infrastructure.adapters.in.api.response;

import java.math.BigDecimal;
import java.util.List;

import com.expo.ordering.domain.model.order.Order;
import com.expo.ordering.domain.model.order.OrderItem;
import com.expo.ordering.domain.model.order.OrderStatus;

/**
 * DTO de salida para representar una orden al cliente HTTP.
 * ✅ La capa API convierte el agregado del dominio a un formato serializable.
 */
public record OrderResponse(
        String orderId,
        String customerId,
        OrderStatus status,
        BigDecimal total,
        List<OrderItemResponse> items
) {

    public record OrderItemResponse(
            String productId,
            String productName,
            int quantity,
            BigDecimal subtotal
    ) {
    }

    public static OrderResponse from(Order order) {
        List<OrderItemResponse> itemResponses = buildItemResponses(order.getItems());
        return new OrderResponse(
                order.getId().toString(),
                order.getCustomerId().value().toString(),
                order.getStatus(),
                order.getTotal().amount(),
                itemResponses
        );
    }

    private static List<OrderItemResponse> buildItemResponses(List<OrderItem> items) {
        return items.stream()
                .map(OrderResponse::toItemResponse)
                .toList();
    }

    private static OrderItemResponse toItemResponse(OrderItem item) {
        return new OrderItemResponse(
                item.getProduct().productId().toString(),
                item.getProduct().productName(),
                item.getQuantity().getValue(),
                item.getSubtotal().amount()
        );
    }
}
