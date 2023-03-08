package org.food.ordering.system.order.service.domain.ports.input.service;

import jakarta.validation.Valid;
import org.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import org.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import org.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;

public interface OrderApplicationService {
    //@Valid para permitir validação dos campos nos dtos passados no parâmetro.
    CreateOrderCommand createOrder(@Valid CreateOrderCommand createOrderCommand);
    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
