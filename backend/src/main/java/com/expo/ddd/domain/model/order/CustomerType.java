package com.expo.ddd.domain.model.order;

/**
 * ✅ Enum de dominio que clasifica a los clientes según sus privilegios de compra.
 *    Los clientes VIP pueden comprar incluso sin stock disponible.
 * ⚠️ En Proyecto A este tipo no existiría como concepto de dominio —
 *    sería un String o int en una tabla de BD sin semántica propia.
 */
public enum CustomerType {
    REGULAR,
    VIP
}
