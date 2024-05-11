package com.example.myapplication.data.dto.restaurant;

import java.util.List;

public class OrderTableDetailsDto {
    private final String waiter;
    private final String cook;
    private final List<DishShortInfoDto> dtos;

    public OrderTableDetailsDto(String waiter, String cook, List<DishShortInfoDto> dtos) {
        this.waiter = waiter;
        this.cook = cook;
        this.dtos = dtos;
    }

    public String getWaiter() {
        return waiter;
    }

    public String getCook() {
        return cook;
    }

    public List<DishShortInfoDto> getDtos() {
        return dtos;
    }
}
