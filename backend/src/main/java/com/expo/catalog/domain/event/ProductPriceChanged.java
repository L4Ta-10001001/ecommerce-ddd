package com.expo.catalog.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class ProductPriceChanged implements DomainEvent {

    private final String productId;
    private final BigDecimal newPrice;
    private final String currency;
    private final LocalDateTime occurredOn;

    public ProductPriceChanged(String productId, BigDecimal newPrice, String currency) {
        this.productId = productId;
        this.newPrice = newPrice;
        this.currency = currency;
        this.occurredOn = LocalDateTime.now();
    }

    @Override
    public LocalDateTime occurredOn() {
        return occurredOn;
    }

    public String getProductId() {
        return productId;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public String getCurrency() {
        return currency;
    }
}