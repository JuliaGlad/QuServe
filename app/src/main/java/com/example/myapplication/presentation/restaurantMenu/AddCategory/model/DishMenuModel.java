package com.example.myapplication.presentation.restaurantMenu.AddCategory.model;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class DishMenuModel {
    private final String dishId;
    private final String name;
    private final String price;
    private final String weight;
    private final Task<Uri> task;

    public DishMenuModel(String dishId, String name, String price, String weight, Task<Uri> task) {
        this.dishId = dishId;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.task = task;
    }

    public Task<Uri> getTask() {
        return task;
    }

    public String getDishId() {
        return dishId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getWeight() {
        return weight;
    }
}
