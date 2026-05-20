package com.expo.ordering.domain.valueObject;

import java.util.Objects;
import java.util.UUID;

/**
 * ✅ Typed ID wrapper. Evita el error clásico de pasar un OrderId donde se espera un CustomerId.
 */
public final class CustomerId {

    private final UUID value;

    private CustomerId(UUID value) {
        this.value = Objects.requireNonNull(value, "CustomerId cannot be null");
    }

    public static CustomerId of(UUID value) {
        return new CustomerId(value);
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof CustomerId customerId)) return false;
        return Objects.equals(value, customerId.value);
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
