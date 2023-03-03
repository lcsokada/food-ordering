package org.food.ordering.system.order.service.domain;

import org.food.ordering.system.order.service.domain.entity.Order;
import org.food.ordering.system.order.service.domain.entity.Restaurant;
import org.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import org.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import org.food.ordering.system.order.service.domain.event.OrderPaidEvent;

import java.util.List;
//Com essa abordagem, os eventos continuam sendo criados no domain core em vez
// de na entidade ou no domain service. Contudo, o disparo de um evento é feito no
//application service. Antes de um evento ser disparado, as regras de negócio devem
//ser persistidas no banco. Se um evento for feito antes da persistencia no banco,
//pode ser que o evento disparado esteja incorreto.
//O domínio não tem conhecimento dos eventos, apenas criando e retornando eventos depois
//de executar alguma regra de negócio. O application service decido quando e como disparar
//eventos.
public interface OrderDomainService {
    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);
    OrderPaidEvent payOrder(Order order);
    void approveOrder(Order order);
    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);
    void cancelOrder(Order order, List<String> failureMessages);
}
