package com.expo.ddd.infrastructure.config;

import com.expo.ddd.application.usecase.CancelOrderUseCase;
import com.expo.ddd.application.usecase.PlaceOrderUseCase;
import com.expo.ddd.domain.repository.OrderRepository;
import com.expo.ddd.domain.repository.ProductRepository;
import com.expo.ddd.infrastructure.persistence.adapter.OrderRepositoryAdapter;
import com.expo.ddd.infrastructure.persistence.adapter.ProductRepositoryAdapter;
import com.expo.ddd.infrastructure.persistence.jpa.OrderJpaRepository;
import com.expo.ddd.infrastructure.persistence.jpa.ProductJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ✅ Configuración de dependencias: conecta puertos con adaptadores.
 *    Los casos de uso reciben las interfaces del dominio, nunca las implementaciones JPA.
 * ⚠️ En Proyecto A, Spring inyecta directamente @Repository en @Service — no hay puertos.
 */
@Configuration
public class BeanConfig {

    @Bean
    public ProductRepository productRepository(ProductJpaRepository jpaRepository) {
        return new ProductRepositoryAdapter(jpaRepository);
    }

    @Bean
    public OrderRepository orderRepository(OrderJpaRepository jpaRepository) {
        return new OrderRepositoryAdapter(jpaRepository);
    }

    @Bean
    public PlaceOrderUseCase placeOrderUseCase(OrderRepository orderRepository,
                                               ProductRepository productRepository) {
        return new PlaceOrderUseCase(orderRepository, productRepository);
    }

    @Bean
    public CancelOrderUseCase cancelOrderUseCase(OrderRepository orderRepository,
                                                 ProductRepository productRepository) {
        return new CancelOrderUseCase(orderRepository, productRepository);
    }
}
