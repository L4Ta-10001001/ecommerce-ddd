package com.expo.catalog.application.port.in;

public interface ReserveStockUseCase {
    void reserve(String productId, int quantity);
}