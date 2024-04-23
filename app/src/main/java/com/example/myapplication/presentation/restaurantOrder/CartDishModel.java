package com.example.myapplication.presentation.restaurantOrder;

import java.util.List;

public class CartDishModel {
    private final String dishId;
    private final String categoryId;
    private final List<String> toRemove;
    private final List<String> topping;
    private final List<String> requiredChoices;

    public CartDishModel(String dishId, String categoryId, List<String> toRemove, List<String> topping, List<String> requiredChoices) {
        this.dishId = dishId;
        this.categoryId = categoryId;
        this.toRemove = toRemove;
        this.topping = topping;
        this.requiredChoices = requiredChoices;
    }

    public String getDishId() {
        return dishId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public List<String> getToRemove() {
        return toRemove;
    }

    public List<String> getTopping() {
        return topping;
    }

    public List<String> getRequiredChoices() {
        return requiredChoices;
    }
}
