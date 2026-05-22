package com.expo.ordering.domain.exception;

public class EmptyOrderException extends RuntimeException {

    public EmptyOrderException() {
        super("Order cannot be placed because it has no items.");
    }
}