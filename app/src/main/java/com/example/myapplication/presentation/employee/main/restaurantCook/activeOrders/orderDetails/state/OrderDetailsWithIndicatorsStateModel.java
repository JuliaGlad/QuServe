package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.orderDetails.state;

import java.util.List;

public class OrderDetailsWithIndicatorsStateModel {
    private final String orderId;
    private final String restaurantId;
    private final String totalPrice;
    private final List<OrderDetailsWithIndicatorsModel> models;

    public OrderDetailsWithIndicatorsStateModel(String orderId,  String restaurantId, String totalPrice, List<OrderDetailsWithIndicatorsModel> models) {
        this.models = models;
        this.restaurantId = restaurantId;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public List<OrderDetailsWithIndicatorsModel> getModels() {
        return models;
    }
}
