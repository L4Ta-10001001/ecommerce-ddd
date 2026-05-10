package com.expo.ddd.domain.model.product;

import com.expo.ddd.domain.exception.InsufficientStockException;
import com.expo.ddd.domain.valueobject.Money;
import com.expo.ddd.domain.valueobject.ProductId;
import com.expo.ddd.domain.valueobject.Quantity;

/**
 * ✅ Aggregate Root del producto. Encapsula las reglas de stock.
 * ⚠️ En Proyecto A, Product sería un @Entity anémico y las reglas de stock
 *    vivirían dispersas en ProductService.
 */
public class Product {

    private final ProductId id;
    private final String name;
    private final Money unitPrice;
    private Quantity stock;

    public Product(ProductId id, String name, Money unitPrice, Quantity stock) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.stock = stock;
    }

    public boolean hasStock(Quantity requested) {
        return !requested.isGreaterThan(stock);
    }

    public void decrementStock(Quantity amount) {
        if (!hasStock(amount)) {
            throw new InsufficientStockException(name);
        }
        stock = stock.subtract(amount);
    }

    public void restoreStock(Quantity amount) {
        stock = stock.add(amount);
    }

    public ProductId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Money getUnitPrice() {
        return unitPrice;
    }

    public Quantity getStock() {
        return stock;
    }
}
