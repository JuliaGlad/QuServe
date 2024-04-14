package com.example.myapplication.data.dto;

public class TableDto {
    private final String tableId;
    private final String tableNumber;

    public TableDto(String tableId, String tableNumber) {
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
