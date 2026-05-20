package com.expo.catalog.infrastructure.adapters.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * ✅ Entidad JPA separada del dominio. El dominio nunca conoce esta clase.
 * ⚠️ En Proyecto A, Product sería @Entity Y Aggregate al mismo tiempo — mezcla de responsabilidades.
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class ProductJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private String status;

    public ProductJpaEntity(
            UUID id,
            String name,
            String description,
            BigDecimal unitPrice,
            String currency,
            int stock,
            String status
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.currency = currency;
        this.stock = stock;
        this.status = status;
    }
}