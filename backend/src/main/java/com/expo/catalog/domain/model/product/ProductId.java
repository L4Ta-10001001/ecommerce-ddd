package com.expo.catalog.domain.model.product;

import java.util.UUID;

/**
 * ✅ Typed ID wrapper para Product. Usa Long porque el ID de producto viene de la BD semilla.
 */

public record ProductId(UUID value) {

    public ProductId {
        if (value == null) {
            throw new IllegalArgumentException("Product id cannot be null");
        }
    }

    public static ProductId from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Product id cannot be null or empty");
        }

        return new ProductId(UUID.fromString(value));
    }
}