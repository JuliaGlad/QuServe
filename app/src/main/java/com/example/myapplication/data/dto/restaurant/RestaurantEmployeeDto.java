package com.example.myapplication.data.dto.restaurant;

public class RestaurantEmployeeDto {

    private final String restaurantId;
    private final String role;
    private final String locationId;

    public RestaurantEmployeeDto(String restaurantId, String locationId, String role) {
        this.restaurantId = restaurantId;
        this.locationId = locationId;
        this.role = role;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getRole() {
        return role;
    }
}
