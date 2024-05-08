package com.example.myapplication.domain.model.restaurant.order;

import java.util.List;

public class OrderCookModel {
    private final String orderId;
    private final String path;
    private final String tableNumber;
    private final List<ActiveOrderDishModel> models;

    public OrderCookModel(String orderId, String path, String tableNumber, List<ActiveOrderDishModel> models) {
        this.orderId = orderId;
        this.path = path;
        this.tableNumber = tableNumber;
        this.models = models;
    }

    public String getPath() {
        return path;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public List<ActiveOrderDishModel> getModels() {
        return models;
    }
}
