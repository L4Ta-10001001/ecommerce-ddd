package com.expo.catalog.application.command;

import java.math.BigDecimal;

public record CreateProductCommand(
        String name,
        String description,
        BigDecimal price,
        String currency,
        Integer stock
) {}
