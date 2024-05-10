package com.example.myapplication.data.dto.restaurant;

public class ReadyDishDto {
    private final String orderDocumentId;
    private final String dishCount;
    private final String tableNumber;
    private final String dishName;

    public ReadyDishDto(String tableNumber, String dishCount, String dishName, String orderDocumentId) {
        this.tableNumber = tableNumber;
        this.dishName = dishName;
        this.dishCount = dishCount;
        this.orderDocumentId = orderDocumentId;
    }

    public String getDishCount() {
        return dishCount;
    }

    public String getOrderDocumentId() {
        return orderDocumentId;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public String getDishName() {
        return dishName;
    }
}
