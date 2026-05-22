package com.expo.catalog.application.port.in;

import com.expo.catalog.application.dto.ProductDto;

public interface GetProductByIdUseCase {
    ProductDto getById(String productId);
}
