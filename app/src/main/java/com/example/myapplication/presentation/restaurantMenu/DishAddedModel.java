package com.example.myapplication.presentation.restaurantMenu;

import android.net.Uri;

public class DishAddedModel {
    private final String dishId;
    private final String price;
    private final String name;
    private final String weightCount;
    private final Uri uri;

    public DishAddedModel(String dishId, String price, String name, String weightCount, Uri uri) {
        this.price = price;
        this.dishId = dishId;
        this.name = name;
        this.weightCount = weightCount;
        this.uri = uri;
    }

    public String getDishId() {
        return dishId;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getWeightCount() {
        return weightCount;
    }

    public Uri getUri() {
        return uri;
    }
}
