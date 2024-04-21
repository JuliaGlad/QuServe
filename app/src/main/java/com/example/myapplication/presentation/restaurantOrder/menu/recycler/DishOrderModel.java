package com.example.myapplication.presentation.restaurantOrder.menu.recycler;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

import myapplication.android.ui.listeners.ButtonItemListener;

public class DishOrderModel {
    private final int id;
    private final String name;
    private final String weight;
    private final String price;
    private final Task<Uri> task;
    private final Uri uri;
    private final ButtonItemListener listener;

    public DishOrderModel(int id, String name, String weight, String price, Uri uri, Task<Uri> task, ButtonItemListener listener) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.uri = uri;
        this.task = task;
        this.listener = listener;
    }

    public boolean compareTo(DishOrderModel other){
        return this.hashCode() == other.hashCode();
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
