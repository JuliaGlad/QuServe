package com.example.myapplication.domain.model.restaurant.table;

public class RestaurantTableModel {
    private final String tableId;
    private final String tableNumber;
    private final String orderId;

    public RestaurantTableModel(String tableId, String tableNumber, String orderId) {
        this.tableId = tableId;
        this.tableNumber = tableNumber;
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTableId() {
        return tableId;
    }

    public String getTableNumber() {
        return tableNumber;
    }
}
