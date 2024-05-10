package com.example.myapplication.data.dto.restaurant;

import java.util.List;

public class DishDto {
    String dishId;
    String name;
    String ingredients;
    String weightCount;
    String price;
    String estimatedTimeCooking;
    List<String> toRemove;

    public DishDto(
            String dishId,
            String name,
            String ingredients,
            String weightCount,
            String price,
            String estimatedTimeCooking,
            List<String> toRemove) {
        this.toRemove = toRemove;
        this.dishId = dishId;
        this.name = name;
        this.ingredients = ingredients;
        this.weightCount = weightCount;
        this.price = price;
        this.estimatedTimeCooking = estimatedTimeCooking;
    }

    public String getDishId() {
        return dishId;
    }

    public String getName() {
        return name;
    }

    public List<String> getToRemove() {
        return toRemove;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getWeightCount() {
        return weightCount;
    }

    public String getPrice() {
        return price;
    }

    public String getEstimatedTimeCooking() {
        return estimatedTimeCooking;
    }

}
