package com.expo.config;

import com.expo.catalog.infrastructure.adapters.out.persistence.entity.ProductJpaEntity;
import com.expo.catalog.infrastructure.adapters.out.persistence.jpa.SpringDataProductRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * ✅ Siembra datos de prueba al iniciar la aplicación.
 *    Usa directamente la entidad JPA — la capa de dominio no participa en el seeding.
 */
@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedProducts(SpringDataProductRepository productJpaRepository) {
        return args -> {
            if (productJpaRepository.count() > 0) {
                return;
            }

            productJpaRepository.save(new ProductJpaEntity(
                    UUID.fromString("11111111-1111-1111-1111-111111111111"),
                    "Laptop",
                    "High-performance laptop",
                    new BigDecimal("1200.00"),
                    "USD",
                    10,
                    "ACTIVE"
            ));

            productJpaRepository.save(new ProductJpaEntity(
                    UUID.fromString("22222222-2222-2222-2222-222222222222"),
                    "Mouse",
                    "Wireless optical mouse",
                    new BigDecimal("25.00"),
                    "USD",
                    50,
                    "ACTIVE"
            ));

            productJpaRepository.save(new ProductJpaEntity(
                    UUID.fromString("33333333-3333-3333-3333-333333333333"),
                    "Keyboard",
                    "Mechanical keyboard",
                    new BigDecimal("75.00"),
                    "USD",
                    30,
                    "ACTIVE"
            ));

            productJpaRepository.save(new ProductJpaEntity(
                    UUID.fromString("44444444-4444-4444-4444-444444444444"),
                    "Monitor",
                    "27-inch IPS monitor",
                    new BigDecimal("350.00"),
                    "USD",
                    5,
                    "ACTIVE"
            ));
        };
    }
}
