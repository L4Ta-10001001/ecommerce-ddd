package com.expo.catalog.domain.specification;

import com.expo.catalog.domain.model.product.ProductName;
import com.expo.catalog.domain.repository.ProductRepository;

public class ProductNameUniqueSpecification {

    private final ProductRepository productRepository;

    public ProductNameUniqueSpecification(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public boolean isSatisfiedBy(ProductName productName) {
        return !productRepository.existsByName(productName.value());
    }
}