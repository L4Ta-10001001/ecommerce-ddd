package com.expo.catalog.application.usecase;

import com.expo.catalog.application.port.in.ReleaseStockUseCase;
import com.expo.catalog.application.port.out.DomainEventPublisher;
import com.expo.catalog.domain.exception.ProductNotFoundException;
import com.expo.catalog.domain.model.product.Product;
import com.expo.catalog.domain.model.product.ProductId;
import com.expo.catalog.domain.repository.ProductRepository;

public class ReleaseStockService implements ReleaseStockUseCase {

    private final ProductRepository productRepository;
    private final DomainEventPublisher eventPublisher;

    public ReleaseStockService(
            ProductRepository productRepository,
            DomainEventPublisher eventPublisher
    ) {
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void release(String productId, int quantity) {
        Product product = productRepository.findById(ProductId.from(productId)
            ).orElseThrow(() -> new ProductNotFoundException(productId));

        product.releaseStock(quantity);

        productRepository.save(product);

        product.pullDomainEvents()
                .forEach(eventPublisher::publish);
    }
}