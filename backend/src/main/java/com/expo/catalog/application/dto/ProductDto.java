package com.expo.catalog.application.dto;
import java.math.BigDecimal;
import com.expo.catalog.domain.model.product.Product;

/*Convierte un obj de dominio en una representacion plana */
public record ProductDto(
        String id,
        String name,
        String description,
        BigDecimal price,
        String currency,
        String status,
        Integer stock
) {

    public static ProductDto from(Product product) {

        return new ProductDto(
                product.getId().value().toString(),
                product.getName().value(),
                product.getDescription().value(),
                product.getPrice().amount(),
                product.getPrice().currency().code(),
                product.getStatus().name(),
                product.getStock().value()
        );
    }
}
