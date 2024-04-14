package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.model;

public class TableModel {
    private final String number;
    private final String tableId;

    public TableModel(String number, String tableId) {
        this.number = number;
        this.tableId = tableId;
    }

    public String getNumber() {
        return number;
    }

    public String getTableId() {
        return tableId;
    }
}
