package com.expo.ordering.domain.model.order;

import java.util.Objects;
import java.util.UUID;

/**
 * ✅ Typed ID wrapper para Order. Previene mezclar IDs de distintos agregados.
 */
public final class OrderId {

    private final UUID value;

    private OrderId(UUID value) {
        this.value = Objects.requireNonNull(value, "OrderId cannot be null");
    }

    public static OrderId of(UUID value) {
        return new OrderId(value);
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof OrderId orderId)) return false;
        return Objects.equals(value, orderId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
