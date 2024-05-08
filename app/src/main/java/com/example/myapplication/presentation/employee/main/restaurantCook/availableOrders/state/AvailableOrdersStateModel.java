package com.example.myapplication.presentation.employee.main.restaurantCook.availableOrders.state;

public class AvailableOrdersStateModel {
    private final String orderId;
    private final String orderPath;
    private final String tableNumber;
    private final String dishesCount;

    public AvailableOrdersStateModel(String orderId, String orderPath, String tableNumber, String dishesCount) {
        this.orderId = orderId;
        this.orderPath = orderPath;
        this.tableNumber = tableNumber;
        this.dishesCount = dishesCount;
    }

    public String getOrderPath() {
        return orderPath;
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
