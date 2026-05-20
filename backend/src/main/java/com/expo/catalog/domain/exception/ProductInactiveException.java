package com.expo.catalog.domain.exception;

public class ProductInactiveException extends RuntimeException {
    public ProductInactiveException() {
        super("You cannot change the price of an inactive product.");
    }
}
