package com.example.myapplication.domain.model.restaurant.order;

import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;

import java.util.List;

public class ActiveOrderDishModel {
    private final String dishPath;
    private final List<VariantCartModel> toppings;
    private final List<String> requiredChoices;
    private final List<String> toRemove;

    public ActiveOrderDishModel(String dishPath, List<VariantCartModel> toppings, List<String> requiredChoices, List<String> toRemove) {
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
