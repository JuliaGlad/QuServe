package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.model;

public class TableModel {
    private final String tableId;
    private final String number;
    private final String orderId;

    public TableModel(String number, String tableId, String orderId) {
        this.number = number;
        this.tableId = tableId;
        this.orderId = orderId;
    }

    public String getNumber() {
        return number;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTableId() {
        return tableId;
    }
}
