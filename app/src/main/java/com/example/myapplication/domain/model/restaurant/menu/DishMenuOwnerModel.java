package com.example.myapplication.domain.model.restaurant.menu;

import java.util.List;

public class DishMenuOwnerModel {
    private final String dishId;
    private final String name;
    private final String weightCount;
    private final String price;

    public DishMenuOwnerModel(String dishId, String name, String weightCount, String price) {
        this.dishId = dishId;
        this.name = name;
        this.weightCount = weightCount;
        this.price = price;
    }


    public String getDishId() {
        return dishId;
    }

    public String getName() {
        return name;
    }

    public String getWeightCount() {
        return weightCount;
    }

    public String getPrice() {
        return price;
    }
}
