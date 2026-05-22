package com.expo.catalog.domain.exception;

public class InvalidStockQuantityException extends RuntimeException {

    public InvalidStockQuantityException() {
        super("The stock quantity must be greater than zero.");
    }

    public InvalidStockQuantityException(int quantity) {
        super("Invalid stock quantity: " + quantity);
    }
}