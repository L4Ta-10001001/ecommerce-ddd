package com.expo.config;

import com.expo.catalog.infrastructure.adapters.out.persistence.entity.ProductJpaEntity;
import com.expo.catalog.infrastructure.adapters.out.persistence.jpa.SpringDataProductRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * ✅ Siembra datos de prueba al iniciar la aplicación.
 *    Usa directamente la entidad JPA — la capa de dominio no participa en el seeding.
 */
@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedProducts(SpringDataProductRepository productJpaRepository) {
        return args -> {
            productJpaRepository.save(new ProductJpaEntity("Laptop", new BigDecimal("1200.00"), 10));
            productJpaRepository.save(new ProductJpaEntity("Mouse", new BigDecimal("25.00"), 50));
            productJpaRepository.save(new ProductJpaEntity("Keyboard", new BigDecimal("75.00"), 30));
            productJpaRepository.save(new ProductJpaEntity("Monitor", new BigDecimal("350.00"), 5));
        };
    }
}
