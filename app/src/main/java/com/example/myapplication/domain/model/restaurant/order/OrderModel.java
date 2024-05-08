package com.example.myapplication.domain.model.restaurant.order;

import java.util.List;

public class OrderModel {
    private final String path;
    private final String restaurantName;
    private final String tableNumber;
    private final String orderId;
    private final String totalPrice;
    private final String isOrdered;
    private final List<ActiveOrderDishModel> models;

    public OrderModel(String path, String orderId, String restaurantName, String tableNumber, String totalPrice, String isOrdered, List<ActiveOrderDishModel> models) {
        this.models = models;
        this.path = path;
        this.tableNumber = tableNumber;
        this.restaurantName = restaurantName;
        this.isOrdered = isOrdered;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }

    public String getPath() {
        return path;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String isOrdered() {
        return isOrdered;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public List<ActiveOrderDishModel> getDtos() {
        return models;
    }
}
