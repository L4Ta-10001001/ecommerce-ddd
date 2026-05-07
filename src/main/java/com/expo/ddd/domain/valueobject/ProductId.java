package com.expo.ddd.domain.valueobject;

import java.util.Objects;

/**
 * ✅ Typed ID wrapper para Product. Usa Long porque el ID de producto viene de la BD semilla.
 */
public final class ProductId {

    private final Long value;

    private ProductId(Long value) {
        this.value = Objects.requireNonNull(value, "ProductId cannot be null");
    }

    public static ProductId of(Long value) {
        return new ProductId(value);
    }

    public Long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof ProductId productId)) return false;
        return Objects.equals(value, productId.value);
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
