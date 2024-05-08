package com.example.myapplication.data.dto;

import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;

import java.util.List;

public class ActiveOrderDishDto {
    private final String docuDishId;
    private final String dishId;
    private final String amount;
    private final String name;
    private final String weight;
    private final String price;
    private final boolean isDone;
    private final List<String> toppings;
    private final List<String> requiredChoices;
    private final List<String> toRemove;

    public ActiveOrderDishDto(String docuDishId, String dishId, String amount, String name, String weight, String price, boolean isDone, List<String> toppings, List<String> requiredChoices, List<String> toRemove) {
        this.dishId = dishId;
        this.docuDishId = docuDishId;
        this.amount = amount;
        this.name = name;
        this.weight = weight;
        this.isDone = isDone;
        this.price = price;
        this.toppings = toppings;
        this.requiredChoices = requiredChoices;
        this.toRemove = toRemove;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getDocuDishId() {
        return docuDishId;
    }

    public String getDishId() {
        return dishId;
    }

    public String getAmount() {
        return amount;
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
