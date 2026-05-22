package com.expo.ordering.domain.exception;

public class InvalidOrderStateException extends RuntimeException {

    public InvalidOrderStateException(String message) {
        super(message);
    }

    public InvalidOrderStateException(String expectedState, String actualState) {
        super("Invalid order state. Expected: " + expectedState + ", but was: " + actualState);
    }
}