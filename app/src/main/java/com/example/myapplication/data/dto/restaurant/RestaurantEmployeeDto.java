package com.example.myapplication.data.dto.restaurant;

public class RestaurantEmployeeDto {

    private final String restaurantId;
    private final String role;
    private final String locationId;
    private final String restaurantName;

    public RestaurantEmployeeDto(String restaurantId, String locationId, String role, String restaurantName) {
        this.restaurantId = restaurantId;
        this.locationId = locationId;
        this.role = role;
        this.restaurantName = restaurantName;
    }

    public String getRestaurantName() {
        return restaurantName;
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
