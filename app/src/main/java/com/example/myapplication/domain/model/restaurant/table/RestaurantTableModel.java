package com.example.myapplication.domain.model.restaurant.table;

public class RestaurantTableModel {
    private final String tableId;
    private final String tableNumber;

    public RestaurantTableModel(String tableId, String tableNumber) {
        this.tableId = tableId;
        this.tableNumber = tableNumber;
    }

    public String getTableId() {
        return tableId;
    }

    public String getTableNumber() {
        return tableNumber;
    }
}
