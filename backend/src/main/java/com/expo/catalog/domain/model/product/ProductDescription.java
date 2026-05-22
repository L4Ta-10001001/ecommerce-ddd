package com.expo.catalog.domain.model.product;

public record ProductDescription(String value) {

    public ProductDescription {

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "Description cannot be empty"
            );
        }
    }
}
