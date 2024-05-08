package com.example.myapplication.presentation.home.restaurantUser.state;

public class RestaurantHomeLocationModel {
    private final String locationId;
    private final String location;
    private final String activeOrders;

    public RestaurantHomeLocationModel(String locationId, String location, String activeOrders) {
        this.locationId = locationId;
        this.location = location;
        this.activeOrders = activeOrders;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getLocation() {
        return location;
    }

    public String getActiveOrders() {
        return activeOrders;
    }
}
