package com.example.myapplication.presentation.restaurantOrder;

public class VariantCartModel {

    private final String name;
    private final String price;

    public VariantCartModel(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
