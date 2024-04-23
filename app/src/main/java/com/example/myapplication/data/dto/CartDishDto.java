package com.example.myapplication.data.dto;

import java.util.List;

public class CartDishDto {

    private final String dishId;
    private final String categoryId;
    private final String amount;
    private final List<String> toppings;
    private final List<String> requiredChoices;
    private final List<String> toRemove;

    public CartDishDto(String dishId, String categoryId, String amount, List<String> toppings, List<String> requiredChoices, List<String> toRemove) {
        this.dishId = dishId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.toppings = toppings;
        this.requiredChoices = requiredChoices;
        this.toRemove = toRemove;
    }

    public String getAmount() {
        return amount;
    }

    public String getDishId() {
        return dishId;
    }

    public String getCategoryId() {
        return categoryId;
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
