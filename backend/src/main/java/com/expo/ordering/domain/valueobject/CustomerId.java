package com.expo.ordering.domain.valueobject;

import java.util.UUID;

/**
 * ✅ Typed ID wrapper. Evita el error clásico de pasar un OrderId donde se espera un CustomerId.
 */
public record CustomerId(UUID value) {

    public CustomerId {
        if (value == null) {
            throw new IllegalArgumentException("CustomerId id cannot be null");
        }
    }

    public static CustomerId from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("CustomerId id cannot be null or empty");
        }

        return new CustomerId(UUID.fromString(value));
    }
}
