package com.example.myapplication.presentation.common.state;

import android.net.Uri;

import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class OrderDetailsDishModel {
    private final String dishId;
    private final String amount;
    private final String name;
    private final String weight;
    private final String price;
    private final Task<Uri> task;
    private final List<String> topping;
    private final List<String> requiredChoice;
    private final List<String> toRemove;

    public OrderDetailsDishModel(String dishId, String amount, String name, String weight, String price, Task<Uri> task, List<String> topping, List<String> requiredChoice, List<String> toRemove) {
        this.dishId = dishId;
        this.task = task;
        this.amount = amount;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.topping = topping;
        this.requiredChoice = requiredChoice;
        this.toRemove = toRemove;
    }

    public Task<Uri> getTask() {
        return task;
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
