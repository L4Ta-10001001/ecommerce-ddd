package com.expo.ddd.domain.valueobject;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * ✅ Value Object que garantiza que una cantidad siempre sea positiva.
 * El sistema nunca puede tener una cantidad inválida porque este objeto lo impide en construcción.
 */
public final class Quantity {

    private final int value;

    public Quantity(int value) {
        validatePositive(value);
        this.value = value;
    }

    public static Quantity of(int value) {
        return new Quantity(value);
    }

    public Money multiply(Money unitPrice) {
        BigDecimal subtotal = unitPrice.getAmount().multiply(BigDecimal.valueOf(value));
        return new Money(subtotal);
    }

    public boolean isGreaterThan(Quantity other) {
        return this.value > other.value;
    }

    public Quantity subtract(Quantity other) {
        return new Quantity(this.value - other.value);
    }

    public Quantity add(Quantity other) {
        return new Quantity(this.value + other.value);
    }

    public int getValue() {
        return value;
    }

    private void validatePositive(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Quantity must be positive, got: " + value);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Quantity quantity)) return false;
        return value == quantity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
