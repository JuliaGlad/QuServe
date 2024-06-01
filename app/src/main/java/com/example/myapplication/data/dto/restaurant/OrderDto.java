package com.example.myapplication.data.dto.restaurant;

import java.util.List;

public class OrderDto {
    private final String restaurantId;
    private final String tableId;
    private final String restaurntName;
    private final String path;
    private final String orderId;
    private final String tableNumber;
    private final String totalPrice;
    private final String isOrdered;
    private final List<ActiveOrderDishDto> dtos;

    public OrderDto(String orderId, String tableId, String path, String tableNumber, String restaurantId, String restaurntName, String totalPrice, String isOrdered, List<ActiveOrderDishDto> dtos) {
        this.dtos = dtos;
        this.tableId = tableId;
        this.path = path;
        this.restaurntName = restaurntName;
        this.tableNumber = tableNumber;
        this.restaurantId = restaurantId;
        this.isOrdered = isOrdered;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }

    public String getTableId() {
        return tableId;
    }

    public String getPath() {
        return path;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurntName;
    }

    public String isOrdered() {
        return isOrdered;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public List<ActiveOrderDishDto> getDtos() {
        return dtos;
    }
}
