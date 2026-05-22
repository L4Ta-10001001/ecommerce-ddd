package com.expo.shared.domain;

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

    public boolean isGreaterThan(Quantity other) {
        return this.value > other.value;
    }

    public Quantity subtract(Quantity other) {
        int result = this.value - other.value;

        if (result <= 0) {
            throw new IllegalArgumentException("Resulting quantity must be positive");
        }

        return new Quantity(result);
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
