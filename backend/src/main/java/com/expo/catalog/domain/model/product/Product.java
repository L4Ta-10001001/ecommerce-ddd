package com.expo.catalog.domain.model.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.expo.catalog.domain.event.ProductActivated;
import com.expo.catalog.domain.event.ProductCreated;
import com.expo.catalog.domain.event.ProductDeactivated;
import com.expo.catalog.domain.event.ProductPriceChanged;
import com.expo.catalog.domain.exception.InvalidProductPriceException;
import com.expo.catalog.domain.exception.ProductInactiveException;
import com.expo.shared.domain.Money;
import com.expo.shared.domain.event.DomainEvent;

/**
 * ✅ Aggregate Root del producto. Encapsula las reglas de stock.
 * ⚠️ En Proyecto A, Product sería un @Entity anémico y las reglas de stock
 *    vivirían dispersas en ProductService.
 */

    /**
     * ✅ Reserva stock para clientes VIP sin importar disponibilidad.
     *    El stockLevel puede quedar negativo — representa deuda de reabastecimiento.
     */

public class Product {

    private final ProductId id;
    private ProductName name;
    private ProductDescription description;
    private Money price;
    private ProductStatus status;
    private Stock stock;
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public Product(
            ProductId id,
            ProductName name,
            ProductDescription description,
            Money price,
            Stock stock
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.status = ProductStatus.ACTIVE;

        domainEvents.add(new ProductCreated(id.value().toString(), name.value()));
    }

    public void changePrice(Money newPrice) {

        if (status == ProductStatus.INACTIVE) {
            throw new ProductInactiveException();
        }

        if (newPrice == null || newPrice.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidProductPriceException();
        }

        this.price = newPrice;
        domainEvents.add(
            new ProductPriceChanged(
                this.id.value().toString(),
                newPrice.amount(),
                newPrice.currency().toString()
            )
        );
    }

    public void activate() {
        this.status = ProductStatus.ACTIVE;
        domainEvents.add(new ProductActivated(this.id.value().toString()));
    }

    public void deactivate() {
        this.status = ProductStatus.INACTIVE;
        domainEvents.add(new ProductDeactivated(this.id.value().toString()));
    }

    public boolean isActive() {
        return this.status == ProductStatus.ACTIVE;
    }

    public boolean isAvailableForSale() {
        return isActive() && stock.value() > 0;
    }

    public void reserveStock(int quantity) {
        stock.reserve(quantity, status, id, domainEvents);
    }

    public void releaseStock(int quantity) {
        stock.release(quantity, id, domainEvents);
    }

    public void replenishStock(int quantity) {
        stock.replenish(quantity, id, domainEvents);
    }

    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return events;
    }

    public ProductId getId() { return id; }
    public ProductName getName() { return name; }
    public ProductDescription getDescription() { return description; }
    public Money getPrice() { return price; }
    public ProductStatus getStatus() { return status; }
    public Stock getStock(){ return stock; };
    
}