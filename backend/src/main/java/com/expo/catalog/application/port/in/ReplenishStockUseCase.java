package com.expo.catalog.application.port.in;

public interface ReplenishStockUseCase {
    void replenish(String productId, int quantity);
}