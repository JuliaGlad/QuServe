package com.example.myapplication.presentation.restaurantMenu.dishItem;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

import myapplication.android.ui.listeners.ButtonItemListener;

public class DishItemModel {
    private final int id;
    private final String dishId;
    private final String name;
    private final String weight;
    private final String price;
    private final Task<Uri> task;
    private Uri uri;
    private final boolean isAdding;
    private final ButtonItemListener listener;

    public DishItemModel(int id, String dishId, String name, String weight, String price, Uri uri, Task<Uri> task, boolean isAdding, ButtonItemListener listener) {
        this.id = id;
        this.name = name;
        this.dishId = dishId;
        this.weight = weight;
        this.price = price;
        this.isAdding = isAdding;
        this.uri = uri;
        this.task = task;
        this.listener = listener;
    }

    public boolean compareTo(DishItemModel other){
        return this.hashCode() == other.hashCode();
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public boolean isAdding() {
        return isAdding;
    }

    public String getDishId() {
        return dishId;
    }

    public Uri getUri() {
        return uri;
    }

    public int getId() {
        return id;
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

    public Task<Uri> getTask() {
        return task;
    }

    public ButtonItemListener getListener() {
        return listener;
    }
}
