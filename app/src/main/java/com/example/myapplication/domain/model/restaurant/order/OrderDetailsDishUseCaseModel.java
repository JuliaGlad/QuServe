package com.example.myapplication.domain.model.restaurant.order;

import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;

import java.util.List;

public class OrderDetailsDishUseCaseModel {
    private final String dishId;
    private final String documentDishId;
    private final String amount;
    private final String name;
    private final String weight;
    private final String price;
    private final List<String> topping;
    private final List<String> requiredChoice;
    private final List<String> toRemove;

    public OrderDetailsDishUseCaseModel(String dishId, String documentDishId, String amount, String name, String weight, String price, List<String> topping, List<String> requiredChoice, List<String> toRemove) {
        this.dishId = dishId;
        this.documentDishId = documentDishId;
        this.amount = amount;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.topping = topping;
        this.requiredChoice = requiredChoice;
        this.toRemove = toRemove;
    }

    public String getDocumentDishId() {
        return documentDishId;
    }

    public String getDishId() {
        return dishId;
    }

    public String getAmount() {
        return amount;
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

    public List<String> getTopping() {
        return topping;
    }

    public List<String> getRequiredChoice() {
        return requiredChoice;
    }

    public List<String> getToRemove() {
        return toRemove;
    }
}
