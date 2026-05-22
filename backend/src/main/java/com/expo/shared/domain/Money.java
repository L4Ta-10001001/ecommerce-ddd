package com.expo.shared.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * ✅ Value Object inmutable. Su identidad está definida por su valor, no por un ID.
 * ⚠️ En Proyecto A, el dinero sería un simple BigDecimal sin ninguna validación encapsulada.
 */

public record Money(BigDecimal amount, Currency currency) {

    private static final int SCALE = 2;

    public Money {
        validateAmount(amount);
        validateCurrency(currency);
        amount = normalize(amount);
    }

    public static Money zero(Currency currency) {
        return new Money(BigDecimal.ZERO, currency);
    }

    public Money add(Money other) {
        ensureSameCurrency(other);

        return new Money(
                this.amount.add(other.amount),
                this.currency
        );
    }

    public Money subtract(Money other) {
        ensureSameCurrency(other);

        return new Money(
                this.amount.subtract(other.amount),
                this.currency
        );
    }

    public Money multiply(int factor) {
        if (factor <= 0) {
            throw new IllegalArgumentException("Factor must be positive");
        }

        return new Money(
            this.amount.multiply(BigDecimal.valueOf(factor)),
            this.currency
        );
    }

    private static void validateAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money amount cannot be negative");
        }
    }

    private static void validateCurrency(Currency currency) {

        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
    }

    /*Normaliza la cantidad de decimales del dinero. */
    private static BigDecimal normalize(BigDecimal amount) {
        return amount.setScale(SCALE, RoundingMode.HALF_UP);
    }

    private void ensureSameCurrency(Money other) {
        if (!currency.equals(other.currency())) {
            throw new IllegalArgumentException(
                    "Cannot operate with different currencies"
            );
        }
    }

    @Override
    public String toString() {
        return amount + " " + currency.code();
    }
}