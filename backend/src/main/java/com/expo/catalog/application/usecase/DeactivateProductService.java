package com.expo.catalog.application.usecase;

import com.expo.catalog.application.port.in.DeactivateProductUseCase;
import com.expo.catalog.application.port.out.DomainEventPublisher;
import com.expo.catalog.domain.exception.ProductNotFoundException;
import com.expo.catalog.domain.model.product.Product;
import com.expo.catalog.domain.model.product.ProductId;
import com.expo.catalog.domain.repository.ProductRepository;

public class DeactivateProductService implements DeactivateProductUseCase {

    private final ProductRepository productRepository;
    private final DomainEventPublisher eventPublisher;

    public DeactivateProductService(ProductRepository productRepository, DomainEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void deactivate(String productId) {
        Product product = productRepository.findById(ProductId.from(productId))
                .orElseThrow(() -> new ProductNotFoundException(productId));

        product.deactivate();
        productRepository.save(product);

        product.pullDomainEvents().forEach(eventPublisher::publish);
    }
}

