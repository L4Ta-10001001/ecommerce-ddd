package com.expo.ddd.infrastructure.config;

import com.expo.ddd.infrastructure.persistence.entity.ProductEntity;
import com.expo.ddd.infrastructure.persistence.jpa.ProductJpaRepository;
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
    public CommandLineRunner seedProducts(ProductJpaRepository productJpaRepository) {
        return args -> {
            productJpaRepository.save(new ProductEntity("Laptop", new BigDecimal("1200.00"), 10));
            productJpaRepository.save(new ProductEntity("Mouse", new BigDecimal("25.00"), 50));
            productJpaRepository.save(new ProductEntity("Keyboard", new BigDecimal("75.00"), 30));
            productJpaRepository.save(new ProductEntity("Monitor", new BigDecimal("350.00"), 5));
        };
    }
}
