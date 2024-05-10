package com.example.myapplication.data.dto.restaurant;

public class OrderShortDto {
    private final String orderId;
    private final String path;
    private final String tableNumber;
    private final String dishesCount;
    private final String isOrdered;

    public OrderShortDto(String orderId, String path, String tableNumber, String dishesCount, String isOrdered) {
        this.orderId = orderId;
        this.path = path;
        this.tableNumber = tableNumber;
        this.isOrdered = isOrdered;
        this.dishesCount = dishesCount;
    }

    public String getPath() {
        return path;
    }

    public String isOrdered() {
        return isOrdered;
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
