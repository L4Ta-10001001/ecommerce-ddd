package com.expo.ddd.domain.valueobject;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * ✅ Value Object inmutable. Su identidad está definida por su valor, no por un ID.
 * ⚠️ En Proyecto A, el dinero sería un simple BigDecimal sin ninguna validación encapsulada.
 */
public final class Money {

    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        validateNonNegative(amount);
        this.amount = amount;
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount);
    }

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public BigDecimal getAmount() {
        return amount;
    }

    private void validateNonNegative(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money amount cannot be negative");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Money money)) return false;
        return amount.compareTo(money.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount.stripTrailingZeros());
    }

    @Override
    public String toString() {
        return "$" + amount;
    }
}
