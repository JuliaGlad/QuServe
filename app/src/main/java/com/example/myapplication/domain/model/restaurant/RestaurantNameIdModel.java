package com.example.myapplication.domain.model.restaurant;

public class RestaurantNameIdModel {
    private final String name;
    private final String id;

    public RestaurantNameIdModel(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
