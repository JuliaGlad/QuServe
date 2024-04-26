package com.example.myapplication.domain.model.restaurant.order;

import java.util.List;

public class OrderModel {
    private final String orderId;
    private final String totalPrice;
    private final boolean isOrdered;
    private final List<ActiveOrderDishModel> dtos;

    public OrderModel(String orderId, String totalPrice, boolean isOrdered, List<ActiveOrderDishModel> dtos) {
        this.dtos = dtos;
        this.isOrdered = isOrdered;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }

    public boolean isOrdered() {
        return isOrdered;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public List<ActiveOrderDishModel> getDtos() {
        return dtos;
    }
}
