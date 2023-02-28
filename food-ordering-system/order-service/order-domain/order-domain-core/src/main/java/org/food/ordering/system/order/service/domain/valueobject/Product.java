package org.food.ordering.system.order.service.domain.valueobject;

import org.food.ordering.system.domain.entity.BaseEntity;
import org.food.ordering.system.domain.valueobject.Money;
import org.food.ordering.system.domain.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productId ,String name, Money price) {
        super.setId(productId);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    //não é necessário o override de equals e hashcode,
    //já que esses já são implementados na classe base
}
