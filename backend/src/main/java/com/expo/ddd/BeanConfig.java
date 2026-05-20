package com.expo.ddd;

import com.expo.catalog.domain.repository.ProductRepository;
import com.expo.catalog.infrastructure.adapters.out.persistence.adapter.ProductRepositoryAdapter;
import com.expo.catalog.infrastructure.adapters.out.persistence.jpa.SpringDataProductRepository;
import com.expo.ordering.application.port.in.CancelOrderUseCase;
import com.expo.ordering.application.port.in.PlaceOrderUseCase;
import com.expo.ordering.domain.repository.OrderRepository;
import com.expo.ordering.infrastructure.adapters.out.persistence.adapter.OrderRepositoryAdapter;
import com.expo.ordering.infrastructure.adapters.out.persistence.jpa.SpringDataOrderRepository;

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
    public ProductRepository productRepository(SpringDataProductRepository jpaRepository) {
        return new ProductRepositoryAdapter(jpaRepository);
    }

    @Bean
    public OrderRepository orderRepository(SpringDataOrderRepository jpaRepository) {
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
