package com.expo.catalog.domain.event;

import java.time.LocalDateTime;

public final class StockReplenished implements DomainEvent {

    private final String productId;
    private final int replenishedQuantity;
    private final int currentStock;
    private final LocalDateTime occurredOn;

    public StockReplenished(
            String productId,
            int replenishedQuantity,
            int currentStock
    ) {
        this.productId = productId;
        this.replenishedQuantity = replenishedQuantity;
        this.currentStock = currentStock;
        this.occurredOn = LocalDateTime.now();
    }

    @Override
    public LocalDateTime occurredOn() {
        return occurredOn;
    }

    public String getProductId() {
        return productId;
    }

    public int getReplenishedQuantity() {
        return replenishedQuantity;
    }

    public int getCurrentStock() {
        return currentStock;
    }
}