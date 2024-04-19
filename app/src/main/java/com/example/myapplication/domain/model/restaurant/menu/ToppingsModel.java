package com.example.myapplication.domain.model.restaurant.menu;

public class ToppingsModel {
    private final String name;
    private final String price;

    public ToppingsModel(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
