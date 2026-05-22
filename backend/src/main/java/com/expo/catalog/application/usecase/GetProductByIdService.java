package com.expo.catalog.application.usecase;

import com.expo.catalog.application.dto.ProductDto;
import com.expo.catalog.application.port.in.GetProductByIdUseCase;
import com.expo.catalog.domain.exception.ProductNotFoundException;
import com.expo.catalog.domain.model.product.Product;
import com.expo.catalog.domain.model.product.ProductId;
import com.expo.catalog.domain.repository.ProductRepository;

public class GetProductByIdService implements GetProductByIdUseCase {

    private final ProductRepository productRepository;

    public GetProductByIdService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto getById(String productId) {
        Product product = productRepository.findById(ProductId.from(productId))
                .orElseThrow(() -> new ProductNotFoundException(productId));

        return ProductDto.from(product);
    }
}

