package com.expo.catalog.infrastructure.mapper;

import com.expo.catalog.domain.model.product.Product;
import com.expo.catalog.domain.model.product.ProductId;
import com.expo.catalog.domain.model.shared.Money;
import com.expo.catalog.infrastructure.adapters.out.persistence.entity.ProductJpaEntity;
import com.expo.ordering.domain.model.shared.Quantity;

/**
 * ✅ Mapper responsable de convertir entre el dominio y la infraestructura JPA.
 *    Esta clase es el puente que mantiene las dos capas completamente desacopladas.
 * ⚠️ En Proyecto A no existe esta separación — la entidad JPA ES el modelo de dominio.
 */
public class ProductMapper {

    public static Product toDomain(ProductJpaEntity entity) {
        return new Product(
                ProductId.of(entity.getId()),
                entity.getName(),
                new Money(entity.getUnitPrice()),
                new Quantity(entity.getStock())
        );
    }

    public static ProductJpaEntity toEntity(Product product) {
        ProductJpaEntity entity = new ProductJpaEntity();
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
