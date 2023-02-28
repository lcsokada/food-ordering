package org.food.ordering.system.order.service.domain.valueobject;

import org.food.ordering.system.domain.valueobject.BaseId;

//A geração de ids únicos é importante somente para os
//aggregates, não necessitando de um UUID aqui
public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}
