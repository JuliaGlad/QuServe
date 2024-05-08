package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.state;

public class CookActiveOrderStateModel {
    private final String tableNumber;
    private final String orderId;
    private final String dishesCount;
    private final String path;

    public CookActiveOrderStateModel(String tableNumber, String path, String orderId, String dishesCount) {
        this.tableNumber = tableNumber;
        this.orderId = orderId;
        this.path = path;
        this.dishesCount = dishesCount;
    }

    public String getPath() {
        return path;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDishesCount() {
        return dishesCount;
    }
}
