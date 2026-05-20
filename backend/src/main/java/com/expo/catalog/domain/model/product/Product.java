package com.expo.catalog.domain.model.product;

import com.expo.catalog.domain.model.shared.Money;
import com.expo.ordering.domain.exception.InsufficientStockException;
import com.expo.ordering.domain.model.shared.Quantity;

/**
 * ✅ Aggregate Root del producto. Encapsula las reglas de stock.
 * ⚠️ En Proyecto A, Product sería un @Entity anémico y las reglas de stock
 *    vivirían dispersas en ProductService.
 */
public class Product {

    private final ProductId id;
    private final String name;
    private final Money unitPrice;
    // Stock interno como int para soportar deuda de reabastecimiento VIP (puede ser negativo)
    private int stockLevel;

    public Product(ProductId id, String name, Money unitPrice, Quantity stock) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.stockLevel = stock.getValue();
    }

    public boolean hasStock(Quantity requested) {
        return stockLevel >= requested.getValue();
    }

    public void decrementStock(Quantity amount) {
        if (!hasStock(amount)) {
            throw new InsufficientStockException(name);
        }
        stockLevel -= amount.getValue();
    }

    /**
     * ✅ Reserva stock para clientes VIP sin importar disponibilidad.
     *    El stockLevel puede quedar negativo — representa deuda de reabastecimiento.
     */
    public void reserveStockForVip(Quantity amount) {
        stockLevel -= amount.getValue();
    }

    public void restoreStock(Quantity amount) {
        stockLevel += amount.getValue();
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
        return Quantity.of(Math.max(1, stockLevel));
    }

    public int getStockLevel() {
        return stockLevel;
    }
}
