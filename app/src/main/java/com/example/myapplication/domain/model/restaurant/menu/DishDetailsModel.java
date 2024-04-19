package com.example.myapplication.domain.model.restaurant.menu;

import java.util.List;

public class DishDetailsModel {
    private final String dishId;
    private final String name;
    private final String weightCount;
    private final String price;
    private final String estimatedTimeCooking;
    private final String ingredients;
    private final List<String> toRemove;

    public DishDetailsModel(String dishId, String name, String weightCount, String price, String estimatedTimeCooking, String ingredients, List<String> toRemove) {
        this.dishId = dishId;
        this.name = name;
        this.weightCount = weightCount;
        this.price = price;
        this.ingredients = ingredients;
        this.estimatedTimeCooking = estimatedTimeCooking;
        this.toRemove = toRemove;
    }

    public List<String> getToRemove() {
        return toRemove;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getEstimatedTimeCooking() {
        return estimatedTimeCooking;
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
