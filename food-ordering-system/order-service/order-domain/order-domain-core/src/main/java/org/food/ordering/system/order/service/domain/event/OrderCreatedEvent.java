package org.food.ordering.system.order.service.domain.event;

import org.food.ordering.system.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

//A interface DomainEvent marca a classe como um domain event e o tipo do evento
public class OrderCreatedEvent extends OrderEvent {
    public OrderCreatedEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
