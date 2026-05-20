package com.expo.catalog.domain.event;

import java.time.LocalDateTime;

public final class StockReserved implements DomainEvent {

    private final String productId;
    private final int reservedQuantity;
    private final int remainingStock;
    private final LocalDateTime occurredOn;

    public StockReserved(
            String productId,
            int reservedQuantity,
            int remainingStock
    ) {
        this.productId = productId;
        this.reservedQuantity = reservedQuantity;
        this.remainingStock = remainingStock;
        this.occurredOn = LocalDateTime.now();
    }

    @Override
    public LocalDateTime occurredOn() {
        return occurredOn;
    }

    public String getProductId() {
        return productId;
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    public int getRemainingStock() {
        return remainingStock;
    }
}