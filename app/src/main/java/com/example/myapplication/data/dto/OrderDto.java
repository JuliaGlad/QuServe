package com.example.myapplication.data.dto;

import java.util.List;

public class OrderDto {
    private final String orderId;
    private final String totalPrice;
    private final boolean isOrdered;
    private final List<ActiveOrderDishDto> dtos;

    public OrderDto(String orderId, String totalPrice, boolean isOrdered, List<ActiveOrderDishDto> dtos) {
        this.dtos = dtos;
        this.isOrdered = isOrdered;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }

    public boolean isOrdered() {
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
