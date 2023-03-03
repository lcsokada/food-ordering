package org.food.ordering.system.order.service.domain.event;

import org.food.ordering.system.domain.event.DomainEvent;
import org.food.ordering.system.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

//Como uma instancia de OrderEvents não deve poder ser instanciada, essa
//deve ser abstract.
public abstract class OrderEvent implements DomainEvent<Order> {
    private final Order order;
    private final ZonedDateTime createdAt;

    //Como essa classe tem apenas dois campos, não é necessário um builder
    public OrderEvent(Order order, ZonedDateTime createdAt) {
        this.order = order;
        this.createdAt = createdAt;
    }

    public Order getOrder() {
        return order;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
