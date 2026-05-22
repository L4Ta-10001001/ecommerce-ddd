package com.expo.ordering.domain.exception;

/**
 * ✅ Excepción de dominio: el lenguaje ubiquo del negocio expresado como código.
 * ⚠️ En Proyecto A esta validación viviría en un Service con lógica ad-hoc.
 */
public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String productName) {
        super("Insufficient stock for product: " + productName);
    }

    public InsufficientStockException(String productName, int requested, int available) {
        super("Insufficient stock for product: " + productName +
              ". Requested: " + requested + ", Available: " + available);
    }
}