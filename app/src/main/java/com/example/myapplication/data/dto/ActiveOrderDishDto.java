package com.example.myapplication.data.dto;

import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;

import java.util.List;

public class ActiveOrderDishDto {
    private final String dishPath;
    private final List<VariantCartModel> toppings;
    private final List<String> requiredChoices;
    private final List<String> toRemove;

    public ActiveOrderDishDto(String dishPath, List<VariantCartModel> toppings, List<String> requiredChoices, List<String> toRemove) {
        this.dishPath = dishPath;
        this.toppings = toppings;
        this.requiredChoices = requiredChoices;
        this.toRemove = toRemove;
    }

    public String getDishPath() {
        return dishPath;
    }

    public List<VariantCartModel> getToppings() {
        return toppings;
    }

    public List<String> getRequiredChoices() {
        return requiredChoices;
    }

    public List<String> getToRemove() {
        return toRemove;
    }
}
