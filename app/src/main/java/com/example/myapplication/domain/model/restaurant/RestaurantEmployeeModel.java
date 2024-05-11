package com.example.myapplication.domain.model.restaurant;

public class RestaurantEmployeeModel {
    private final String userId;
    private final String name;
    private final String role;

    public RestaurantEmployeeModel(String userId, String name, String role) {
        this.userId = userId;
        this.name = name;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
