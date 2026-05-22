package com.expo.catalog.infrastructure.events;

import com.expo.catalog.application.port.in.ReleaseStockUseCase;
import com.expo.shared.contracts.events.OrderCancelledEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderCancelledListener {

    private final ReleaseStockUseCase releaseStockUseCase;

    public OrderCancelledListener(ReleaseStockUseCase releaseStockUseCase) {
        this.releaseStockUseCase = releaseStockUseCase;
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrderCancelledEvent event) {
        for (OrderCancelledEvent.OrderItem item : event.getItems()) {
            releaseStockUseCase.release(
                    item.getProductId().toString(),
                    item.getQuantity()
            );
        }
    }
}
