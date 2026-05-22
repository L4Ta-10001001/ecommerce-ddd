package com.expo.catalog.application.port.in;

public interface ReleaseStockUseCase {
    void release(String productId, int quantity);
}