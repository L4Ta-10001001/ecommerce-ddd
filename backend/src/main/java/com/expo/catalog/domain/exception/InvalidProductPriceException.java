package com.expo.catalog.domain.exception;

public class InvalidProductPriceException extends RuntimeException {

    public InvalidProductPriceException() {
        super("The price of the product must be greater than zero.");
    }
}