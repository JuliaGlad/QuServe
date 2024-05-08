package com.example.myapplication.domain.model.restaurant.order;

import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;

import java.util.List;

public class ActiveOrderDishModel {
    private final List<String> toppings;
    private final List<String> requiredChoices;
    private final List<String> toRemove;

    public ActiveOrderDishModel(List<String> toppings, List<String> requiredChoices, List<String> toRemove) {
        this.toppings = toppings;
        this.requiredChoices = requiredChoices;
        this.toRemove = toRemove;
    }

    public List<String> getToppings() {
        return toppings;
    }

    public List<String> getRequiredChoices() {
        return requiredChoices;
    }

    public List<String> getToRemove() {
        return toRemove;
    }
}
