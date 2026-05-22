package com.expo.catalog.infrastructure.events;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.expo.catalog.application.port.in.ReserveStockUseCase;
import com.expo.shared.contracts.events.OrderPlacedEvent;

@Component
public class OrderPlacedListener {
    private final ReserveStockUseCase reserveStockUseCase;

    public OrderPlacedListener(ReserveStockUseCase reserveStockUseCase){
        this.reserveStockUseCase = reserveStockUseCase;
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrderPlacedEvent event) {
        for (OrderPlacedEvent.OrderItem item : event.getItems()) {
            reserveStockUseCase.reserve(
                    item.getProductId().toString(),
                    item.getQuantity()
            );
        }
    }
}
