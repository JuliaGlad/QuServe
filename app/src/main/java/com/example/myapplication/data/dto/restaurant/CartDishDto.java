package com.example.myapplication.data.dto.restaurant;

import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;

import java.util.List;

public class CartDishDto {

    private final String dishId;
    private final String categoryId;
    private String amount;
    private final String name;
    private final String weight;
    private final String price;
    private final List<VariantCartModel> toppings;
    private final List<String> requiredChoices;
    private final List<String> toRemove;

    public CartDishDto(String dishId, String categoryId, String name, String weight, String price, String amount, List<VariantCartModel> toppings, List<String> requiredChoices, List<String> toRemove) {
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

    public String getName() {
        return name;
    }

    public String getWeight() {
        return weight;
    }

    public String getPrice() {
        return price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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
