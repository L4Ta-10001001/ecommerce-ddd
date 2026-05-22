package com.expo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * ✅ Punto de entrada de la aplicación DDD.
 * ⚠️ Contraste vs Proyecto A: aquí el dominio manda; Spring es solo infraestructura.
 */
@SpringBootApplication
@EnableAsync
public class EcommerceDddApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceDddApplication.class, args);
    }
}
