package com.expo.catalog.domain.event;

import java.time.LocalDateTime;

public final class StockReleased implements DomainEvent {

    private final String productId;
    private final int releasedQuantity;
    private final int currentStock;
    private final LocalDateTime occurredOn;

    public StockReleased(
            String productId,
            int releasedQuantity,
            int currentStock
    ) {
        this.productId = productId;
        this.releasedQuantity = releasedQuantity;
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

    public int getReleasedQuantity() {
        return releasedQuantity;
    }

    public int getCurrentStock() {
        return currentStock;
    }
}
