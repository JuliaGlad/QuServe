package com.example.myapplication.domain.model.restaurant.order;

public class DishShortInfoModel {
    private final String name;
    private final String count;

    public DishShortInfoModel(String name, String count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public String getCount() {
        return count;
    }
}
