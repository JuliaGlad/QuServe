package com.example.myapplication.domain.model.restaurant.order;

import com.example.myapplication.data.dto.restaurant.DishShortInfoDto;

import java.util.List;

public class OrderTableDetailsModel {
    private final String waiter;
    private final String cook;
    private final List<DishShortInfoModel> models;

    public OrderTableDetailsModel(String waiter, String cook, List<DishShortInfoModel> models) {
        this.waiter = waiter;
        this.cook = cook;
        this.models = models;
    }

    public String getWaiter() {
        return waiter;
    }

    public String getCook() {
        return cook;
    }

    public List<DishShortInfoModel> getModels() {
        return models;
    }
}
