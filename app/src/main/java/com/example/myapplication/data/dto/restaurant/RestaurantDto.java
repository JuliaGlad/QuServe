package com.example.myapplication.data.dto.restaurant;

public class RestaurantDto {
    private final String restaurantId;
    private final String name;
    private final String email;
    private final String phone;
    private final String owner;

    public RestaurantDto(String restaurantId, String name, String email, String phone, String owner) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.owner = owner;
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

    public String getPhone() {
        return phone;
    }

    public String getOwner() {
        return owner;
    }
}
