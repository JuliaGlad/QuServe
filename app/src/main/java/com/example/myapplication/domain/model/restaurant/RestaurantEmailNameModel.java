package com.example.myapplication.domain.model.restaurant;

public class RestaurantEmailNameModel {
    private final String restaurantId;
    private final String name;
    private final String email;

    public RestaurantEmailNameModel(String restaurantId, String name, String email) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.email = email;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
