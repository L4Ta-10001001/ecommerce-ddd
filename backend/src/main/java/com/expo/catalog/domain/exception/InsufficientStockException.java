package com.expo.catalog.domain.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException() {
        super("There is not enough stock available.");
    }

    public InsufficientStockException(int requested, int available) {
        super("Insufficient stock. Requested: " + requested +
              ", Available: " + available);
    }
}