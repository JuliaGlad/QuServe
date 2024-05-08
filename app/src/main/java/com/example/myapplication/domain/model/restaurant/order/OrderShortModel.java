package com.example.myapplication.domain.model.restaurant.order;

public class OrderShortModel {
    private final String orderId;
    private final String path;
    private final String tableNumber;
    private final String dishesCount;

    public OrderShortModel(String orderId, String path, String tableNumber, String dishesCount) {
        this.orderId = orderId;
        this.path = path;
        this.tableNumber = tableNumber;
        this.dishesCount = dishesCount;
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

    public String getDishesCount() {
        return dishesCount;
    }
}
