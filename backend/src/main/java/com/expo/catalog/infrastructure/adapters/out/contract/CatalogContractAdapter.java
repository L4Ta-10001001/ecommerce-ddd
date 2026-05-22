package com.expo.catalog.infrastructure.adapters.out.contract;

import com.expo.catalog.application.dto.ProductDto;
import com.expo.catalog.application.port.in.GetProductByIdUseCase;
import com.expo.contracts.catalog.CatalogContract;

@org.springframework.stereotype.Component
public class CatalogContractAdapter implements CatalogContract {

    private final GetProductByIdUseCase getProductByIdUseCase;

    public CatalogContractAdapter(GetProductByIdUseCase getProductByIdUseCase) {
        this.getProductByIdUseCase = getProductByIdUseCase;
    }

    @Override
    public com.expo.contracts.catalog.ProductDto getProductById(String productId) {
        ProductDto product = getProductByIdUseCase.getById(productId);

        return new com.expo.contracts.catalog.ProductDto(
                product.id(),
                product.name(),
                product.price(),
                product.stock()
        );
    }
}
