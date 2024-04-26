package com.example.myapplication.presentation.restaurantOrder.restaurantCart.model;

import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;

import java.util.List;

public class OrderDishesModel {
    private final String dishId;
    private final String categoryId;
    private final String price;
    private final String amount;
    private final List<VariantCartModel> toppings;
    private final List<String> toRemove;
    private final List<String> requireChoices;

    public OrderDishesModel(String dishId, String categoryId, String price, String amount, List<VariantCartModel> toppings, List<String> toRemove, List<String> requireChoices) {
        this.dishId = dishId;
        this.price = price;
        this.categoryId = categoryId;
        this.amount = amount;
        this.toppings = toppings;
        this.toRemove = toRemove;
        this.requireChoices = requireChoices;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getAmount() {
        return amount;
    }

    public String getDishId() {
        return dishId;
    }

    public String getPrice() {
        return price;
    }

    public List<VariantCartModel> getToppings() {
        return toppings;
    }

    public List<String> getToRemove() {
        return toRemove;
    }

    public List<String> getRequireChoices() {
        return requireChoices;
    }
}
