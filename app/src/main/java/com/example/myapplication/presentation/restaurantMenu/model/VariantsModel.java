package com.example.myapplication.presentation.restaurantMenu.model;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class VariantsModel {
    private final String name;
    private final String price;
    private final Task<Uri> uri;

    public VariantsModel(String name, String price, Task<Uri> uri) {
        this.name = name;
        this.price = price;
        this.uri = uri;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Task<Uri> getUri() {
        return uri;
    }
}
