package com.expo.ddd.infrastructure.persistence.mapper;

import com.expo.ddd.domain.model.product.Product;
import com.expo.ddd.domain.valueobject.Money;
import com.expo.ddd.domain.valueobject.ProductId;
import com.expo.ddd.domain.valueobject.Quantity;
import com.expo.ddd.infrastructure.persistence.entity.ProductEntity;

/**
 * ✅ Mapper responsable de convertir entre el dominio y la infraestructura JPA.
 *    Esta clase es el puente que mantiene las dos capas completamente desacopladas.
 * ⚠️ En Proyecto A no existe esta separación — la entidad JPA ES el modelo de dominio.
 */
public class ProductMapper {

    public static Product toDomain(ProductEntity entity) {
        return new Product(
                ProductId.of(entity.getId()),
                entity.getName(),
                new Money(entity.getUnitPrice()),
                new Quantity(entity.getStock())
        );
    }

    public static ProductEntity toEntity(Product product) {
        ProductEntity entity = new ProductEntity();
        if (product.getId().getValue() != null) {
            entity.setId(product.getId().getValue());
        }
        entity.setName(product.getName());
        entity.setUnitPrice(product.getUnitPrice().getAmount());
        entity.setStock(product.getStock().getValue());
        return entity;
    }

    private ProductMapper() {
        // Utility class — no instantiation
    }
}
