package com.example.myapplication.domain.model.restaurant.order;

import java.util.List;

public class OrderDetailsModel {
    private final String orderId;
    private final String tableId;
    private final String restaurantId;
    private final String name;
    private final String tableNumber;
    private final String totalPrice;
    private final List<OrderDetailsDishUseCaseModel> models;

    public OrderDetailsModel(String orderId, String tableId, String restaurantId, String name, String tableNumber, String totalPrice, List<OrderDetailsDishUseCaseModel> models) {
        this.orderId = orderId;
        this.tableId = tableId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.tableNumber = tableNumber;
        this.totalPrice = totalPrice;
        this.models = models;
    }

    public String getTableId() {
        return tableId;
    }

    public String getName() {
        return name;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public List<OrderDetailsDishUseCaseModel> getModels() {
        return models;
    }
}
