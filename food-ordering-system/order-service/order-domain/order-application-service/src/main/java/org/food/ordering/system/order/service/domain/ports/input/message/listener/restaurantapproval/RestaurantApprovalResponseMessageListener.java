package org.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval;

import org.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse;

//são tipos especiais de application services, disparados por eventos e não  pelo cliente.
public interface RestaurantApprovalResponseMessageListener {
    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);
    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
