package com.example.myapplication.data.dto.restaurant;

public class TableDto {
    private final String tableId;
    private final String tableNumber;
    private final String orderId;

    public TableDto(String tableId, String tableNumber, String orderId) {
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
