package com.expo.ordering.domain.model.order;

import java.util.UUID;

/**
 * ✅ Typed ID wrapper para Order. Previene mezclar IDs de distintos agregados.
 */
public record OrderId(UUID value) {

    public OrderId {
        if (value == null) {
            throw new IllegalArgumentException("Order id cannot be null");
        }
    }

    public static OrderId newId() {
        return new OrderId(UUID.randomUUID());
    }

    public static OrderId from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Order id cannot be null or empty");
        }

        return new OrderId(UUID.fromString(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}