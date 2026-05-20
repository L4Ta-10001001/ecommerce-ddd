package com.expo.catalog.application.command;

import java.math.BigDecimal;

public record ChangeProductPriceCommand(
    String productId,
    BigDecimal amount,
    String currency
) {}
