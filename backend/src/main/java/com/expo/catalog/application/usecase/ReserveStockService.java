package com.expo.catalog.application.usecase;

import com.expo.catalog.application.port.in.ReserveStockUseCase;
import com.expo.catalog.application.port.out.DomainEventPublisher;
import com.expo.catalog.domain.exception.ProductNotFoundException;
import com.expo.catalog.domain.model.product.Product;
import com.expo.catalog.domain.model.product.ProductId;
import com.expo.catalog.domain.repository.ProductRepository;

public class ReserveStockService implements ReserveStockUseCase {

    private final ProductRepository productRepository;
    private final DomainEventPublisher eventPublisher;

    public ReserveStockService(
            ProductRepository productRepository,
            DomainEventPublisher eventPublisher
    ) {
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void reserve(String productId, int quantity) {
        Product product = productRepository.findById(ProductId.from(productId)
            ).orElseThrow(() -> new ProductNotFoundException(productId));

        product.reserveStock(quantity);

        productRepository.save(product);

        product.pullDomainEvents()
                .forEach(eventPublisher::publish);
    }
}
