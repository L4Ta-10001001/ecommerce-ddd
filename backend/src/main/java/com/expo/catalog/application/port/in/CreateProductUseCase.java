package com.expo.catalog.application.port.in;

import com.expo.catalog.application.command.CreateProductCommand;
import com.expo.catalog.application.dto.ProductDto;

public interface CreateProductUseCase{
    ProductDto execute(CreateProductCommand command);
}