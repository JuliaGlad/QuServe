package com.example.myapplication.presentation.employee.main.restaurantWaiter.main.state;

public class WaiterReadyDishesModel {
    private final String tableNumber;
    private final String dishCount;
    private final String dishName;
    private final String orderDocId;

    public WaiterReadyDishesModel(String tableNumber, String dishCount, String dishName, String orderDocId) {
        this.tableNumber = tableNumber;
        this.dishCount = dishCount;
        this.dishName = dishName;
        this.orderDocId = orderDocId;
    }

    public String getDishCount() {
        return dishCount;
    }

    public String getOrderDocId() {
        return orderDocId;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public String getDishName() {
        return dishName;
    }
}
