package com.expo.catalog.infrastructure.mapper;

import com.expo.catalog.domain.model.product.Product;
import com.expo.catalog.domain.model.product.ProductDescription;
import com.expo.catalog.domain.model.product.ProductId;
import com.expo.catalog.domain.model.product.ProductName;
import com.expo.catalog.domain.model.shared.Currency;
import com.expo.catalog.domain.model.shared.Money;
import com.expo.catalog.infrastructure.adapters.out.persistence.entity.ProductJpaEntity;

/**
 * ✅ Mapper responsable de convertir entre el dominio y la infraestructura JPA.
 *    Esta clase es el puente que mantiene las dos capas completamente desacopladas.
 * ⚠️ En Proyecto A no existe esta separación — la entidad JPA ES el modelo de dominio.
 */
public class ProductMapper {

    public Product toDomain(ProductJpaEntity entity) {
        return new Product(
                new ProductId(entity.getId()),
                new ProductName(entity.getName()),
                new ProductDescription(entity.getDescription()),
                new Money(
                        entity.getUnitPrice(),
                        new Currency(entity.getCurrency())
                )
        );
    }

    public ProductJpaEntity toEntity(Product product) {

        ProductJpaEntity entity = new ProductJpaEntity();

        entity.setId(product.getId().value());
        entity.setName(product.getName().value());
        entity.setDescription(product.getDescription().value());
        entity.setUnitPrice(product.getPrice().amount());
        entity.setCurrency(product.getPrice().currency().code());
        entity.setStatus(product.getStatus().name());

        return entity;
    }

}
