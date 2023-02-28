package org.food.ordering.system.order.service.domain.entity;

import org.food.ordering.system.domain.entity.AggregateRoot;
import org.food.ordering.system.domain.valueobject.*;
import org.food.ordering.system.order.service.domain.exception.OrderDomainException;
import org.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import org.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import org.food.ordering.system.order.service.domain.valueobject.TrackingId;

import java.util.List;
import java.util.UUID;

//Será o aggregate root para o processo de pedido (order process)
public class Order extends AggregateRoot<OrderId> {
    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> items;

    //Esses 3 campos não são final, pois serão modificados
    //de acordo com a regra de negócio.
    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    public void initializeOrder() {
        super.setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initializeOrderItems();
    }

    public void validateOrder() {
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    private void validateItemsPrice() {
        Money orderItemsTotal = items.stream().map(orderItem -> {
            validateItemPrice(orderItem);
            return orderItem.getSubtotal();
        }).reduce(Money.ZERO, Money::add);
        //para usar o reduce (somar tudo) é necessário criar
        //uma constante Money ZERO

        if(!price.equals(orderItemsTotal)) {
            throw new OrderDomainException("Total price: " + price.getAmount()
                    + "is not equal to Order items total: " + orderItemsTotal.getAmount() + "!");
        }
    }

    //valida o preço do item de acordo com o que é persistido
    // na store. Esse responsabilidade será delegada ao OrderItem
    private void validateItemPrice(OrderItem orderItem) {
        if(!orderItem.isPriceValid()) {
            throw new OrderDomainException("Order item price: " + orderItem.getPrice().getAmount() +
                    "is not valid for product " + orderItem.getProduct().getId().getValue());
        }
    }

    private void validateTotalPrice() {
        if(price == null || !price.isGreaterThanZero()) {
            throw new OrderDomainException("Total price must be greater tha zero!");
        }
    }

    private void validateInitialOrder() {
        if(orderStatus != null || getId() != null) {
            throw new OrderDomainException("Order is not in correct state for initialization!");
        }
    }

    private void initializeOrderItems() {
        long itemId = 1;
        for(OrderItem orderItem: items) {
            orderItem.initializeOrderItem(super.getId(), new OrderItemId(itemId++));
        }
    }

    private Order(Builder builder) {
        super.setId(builder.id);
        customerId = builder.customerId;
        restaurantId = builder.restaurantId;
        deliveryAddress = builder.deliveryAddress;
        price = builder.price;
        items = builder.items;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessages = builder.failureMessages;
    }


    public CustomerId getCustomerId() {
        return customerId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public StreetAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }


    public static final class Builder {
        private OrderId id;
        private CustomerId customerId;
        private RestaurantId restaurantId;
        private StreetAddress deliveryAddress;
        private Money price;
        private List<OrderItem> items;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessages;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(OrderId val) {
            id = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder restaurantId(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder deliveryAddress(StreetAddress val) {
            deliveryAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
