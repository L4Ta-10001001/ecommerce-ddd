package com.expo.catalog.infrastructure.adapters.out.event;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class CatalogDomainEventListener {

    private final SimpMessagingTemplate messagingTemplate;

    public CatalogDomainEventListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handle(Object event) {
        System.out.println("Domain Event recibido: " + event);

        // Enviar el evento al frontend mediante WebSocket
        messagingTemplate.convertAndSend(
                "/topic/catalog/events",
                event
        );
    }
}