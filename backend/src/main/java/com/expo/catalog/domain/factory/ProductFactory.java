package com.expo.catalog.domain.factory;

import java.math.BigDecimal;
import java.util.UUID;

import com.expo.catalog.domain.model.product.Product;
import com.expo.catalog.domain.model.product.ProductDescription;
import com.expo.catalog.domain.model.product.ProductName;
import com.expo.catalog.domain.model.product.Stock;
import com.expo.shared.domain.Currency;
import com.expo.shared.domain.Money;
import com.expo.catalog.domain.model.product.ProductId;


public class ProductFactory {

    public Product create(
            String name,
            String description,
            BigDecimal price,
            String currency,
            Integer stock
    ) {
        return new Product(
                new ProductId(UUID.randomUUID()),
                new ProductName(name),
                new ProductDescription(description),
                new Money(price, new Currency(currency)),
                new Stock(stock)
        );
    }
}