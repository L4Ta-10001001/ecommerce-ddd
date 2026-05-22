package com.expo.shared.domain;

public record Currency(String code) {

    public Currency {

        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException(
                    "Currency code cannot be empty"
            );
        }

        code = code.toUpperCase();

        try {
            java.util.Currency.getInstance(code);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "Invalid currency code: " + code
            );
        }
    }

    @Override
    public String toString() {
        return code;
    }
}