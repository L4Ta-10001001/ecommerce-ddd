package com.expo.catalog.domain.model.product;

import com.expo.catalog.domain.event.StockReleased;
import com.expo.catalog.domain.event.StockReplenished;
import com.expo.catalog.domain.event.StockReserved;
import com.expo.catalog.domain.exception.InsufficientStockException;
import com.expo.catalog.domain.exception.InvalidStockQuantityException;
import com.expo.catalog.domain.exception.ProductInactiveException;
import com.expo.shared.domain.event.DomainEvent;

import java.util.List;
public class Stock {

    private int quantity;

    public Stock(int quantity) {
        if (quantity < 0) {
            throw new InvalidStockQuantityException();
        }
        this.quantity = quantity;
    }

    public void reserve(
            int requestedQuantity,
            ProductStatus status,
            ProductId productId,
            List<DomainEvent> domainEvents
    ) {
        if (status == ProductStatus.INACTIVE) {
            throw new ProductInactiveException();
        }

        validatePositive(requestedQuantity);

        if (this.quantity < requestedQuantity) {
            throw new InsufficientStockException();
        }

        this.quantity -= requestedQuantity;

        domainEvents.add(
                new StockReserved(
                        productId.value().toString(),
                        requestedQuantity,
                        this.quantity
                )
        );
    }

    public void release(
            int releasedQuantity,
            ProductId productId,
            List<DomainEvent> domainEvents
    ) {
        validatePositive(releasedQuantity);

        this.quantity += releasedQuantity;

        domainEvents.add(
                new StockReleased(
                        productId.value().toString(),
                        releasedQuantity,
                        this.quantity
                )
        );
    }

    public void replenish(
            int addedQuantity,
            ProductId productId,
            List<DomainEvent> domainEvents
    ) {
        validatePositive(addedQuantity);

        this.quantity += addedQuantity;

        domainEvents.add(
                new StockReplenished(
                        productId.value().toString(),
                        addedQuantity,
                        this.quantity
                )
        );
    }

    public int value() {
        return this.quantity;
    }

    public boolean hasEnough(int requestedQuantity) {
        return this.quantity >= requestedQuantity;
    }

    private void validatePositive(int quantity) {
        if (quantity <= 0) {
            throw new InvalidStockQuantityException();
        }
    }
}