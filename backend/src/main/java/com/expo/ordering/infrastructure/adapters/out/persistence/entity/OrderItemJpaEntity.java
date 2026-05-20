package com.expo.ordering.infrastructure.adapters.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import com.expo.catalog.infrastructure.adapters.out.persistence.entity.ProductJpaEntity;

/**
 * 🔌 Entidad JPA que representa un ítem de una orden en base de datos.
 */
@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
public class OrderItemJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderJpaEntity order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductJpaEntity product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;
}
