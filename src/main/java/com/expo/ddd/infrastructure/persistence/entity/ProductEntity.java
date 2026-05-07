package com.expo.ddd.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * ✅ Entidad JPA separada del dominio. El dominio nunca conoce esta clase.
 * ⚠️ En Proyecto A, Product sería @Entity Y Aggregate al mismo tiempo — mezcla de responsabilidades.
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private int stock;

    public ProductEntity(String name, BigDecimal unitPrice, int stock) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.stock = stock;
    }
}
