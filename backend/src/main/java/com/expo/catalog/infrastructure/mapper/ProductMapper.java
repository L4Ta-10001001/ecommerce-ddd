package com.expo.catalog.infrastructure.mapper;

import com.expo.catalog.domain.model.product.Product;
import com.expo.catalog.domain.model.product.ProductDescription;
import com.expo.catalog.domain.model.product.ProductId;
import com.expo.catalog.domain.model.product.ProductName;
import com.expo.catalog.domain.model.product.ProductStatus;
import com.expo.catalog.domain.model.product.Stock;
import com.expo.catalog.infrastructure.adapters.out.persistence.entity.ProductJpaEntity;
import com.expo.shared.domain.Currency;
import com.expo.shared.domain.Money;

/**
 * ✅ Mapper responsable de convertir entre el dominio y la infraestructura JPA.
 *    Esta clase es el puente que mantiene las dos capas completamente desacopladas.
 * ⚠️ En Proyecto A no existe esta separación — la entidad JPA ES el modelo de dominio.
 */
public class ProductMapper {

    public Product toDomain(ProductJpaEntity entity) {
        Product product = new Product(
                new ProductId(entity.getId()),
                new ProductName(entity.getName()),
                new ProductDescription(entity.getDescription()),
                new Money(
                        entity.getUnitPrice(),
                        new Currency(entity.getCurrency())
                ),
                new Stock(entity.getStock())
        );

        // Restaurar estado persistido
        if (entity.getStatus() != null &&
                ProductStatus.valueOf(entity.getStatus()) == ProductStatus.INACTIVE) {
            product.deactivate();
            product.pullDomainEvents(); // limpiar evento generado al reconstruir
        }

        // Limpiar ProductCreated generado por el constructor
        product.pullDomainEvents();

        return product;
    }

    public ProductJpaEntity toEntity(Product product) {

        ProductJpaEntity entity = new ProductJpaEntity();

        entity.setId(product.getId().value());
        entity.setName(product.getName().value());
        entity.setDescription(product.getDescription().value());
        entity.setUnitPrice(product.getPrice().amount());
        entity.setCurrency(product.getPrice().currency().code());
        entity.setStatus(product.getStatus().name());
        entity.setStock(product.getStock().value());

        return entity;
    }

}
