package com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.cartDishItem;

import android.net.Uri;

import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;
import com.google.android.gms.tasks.Task;

import java.util.List;

import myapplication.android.ui.listeners.ButtonItemListener;
import myapplication.android.ui.listeners.ButtonStringListener;

public class CartDishItemModel {
    int id;
    String dishId;
    String categoryId;
    String name;
    String weight;
    String amount;
    String price;
    Task<Uri> task;
    List<String> toRemove;
    List<VariantCartModel> topping;
    List<String> requiredChoices;
    ButtonItemListener addListener;
    ButtonStringListener removeListener;

    public CartDishItemModel(int id, String dishId, String categoryId , String name, String weight, String price, String amount, Task<Uri> task, List<String> toRemove, List<VariantCartModel> topping, List<String> requiredChoices, ButtonItemListener addListener, ButtonStringListener removeListener) {
        this.id = id;
        this.dishId = dishId;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.amount = amount;
        this.task = task;
        this.toRemove = toRemove;
        this.topping = topping;
        this.requiredChoices = requiredChoices;
        this.addListener = addListener;
        this.removeListener = removeListener;
    }

    public boolean compareTo(CartDishItemModel other){
        return this.hashCode() == other.hashCode();
    }

    public int getId() {
        return id;
    }

    public String getDishId() {
        return dishId;
    }

    public String getCategoryId() {
        return categoryId;
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

    public String getPrice() {
        return price;
    }

    public Task<Uri> getTask() {
        return task;
    }

    public List<String> getToRemove() {
        return toRemove;
    }

    public List<VariantCartModel> getTopping() {
        return topping;
    }

    public List<String> getRequiredChoices() {
        return requiredChoices;
    }
}
