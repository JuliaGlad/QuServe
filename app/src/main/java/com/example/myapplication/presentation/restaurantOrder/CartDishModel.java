package com.example.myapplication.presentation.restaurantOrder;

import com.example.myapplication.presentation.restaurantMenu.model.VariantsModel;

import java.util.List;

public class CartDishModel {
    private final String dishId;
    private final String categoryId;
    private final String amount;
    private final String name;
    private final String weight;
    private final String price;
    private final List<VariantCartModel> toppings;
    private final List<String> requiredChoices;
    private final List<String> toRemove;

    public CartDishModel(String dishId, String categoryId, String name, String weight, String price, String amount, List<VariantCartModel> toppings, List<String> requiredChoices, List<String> toRemove) {
        this.dishId = dishId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.toppings = toppings;
        this.requiredChoices = requiredChoices;
        this.toRemove = toRemove;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getWeight() {
        return weight;
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
