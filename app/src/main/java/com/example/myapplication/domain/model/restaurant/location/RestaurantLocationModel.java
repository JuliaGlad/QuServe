package com.example.myapplication.domain.model.restaurant.location;

public class RestaurantLocationModel {
    private final String locationId;
    private final String location;
    private final String city;
    private final String cooksCount;
    private final String waitersCount;
    private final String ordersCount;

    public RestaurantLocationModel(String locationId, String location, String city, String cooksCount, String waitersCount, String ordersCount) {
        this.locationId = locationId;
        this.location = location;
        this.city = city;
        this.cooksCount = cooksCount;
        this.waitersCount = waitersCount;
        this.ordersCount = ordersCount;
    }

    public String getOrdersCount() {
        return ordersCount;
    }

    public String getLocation() {
        return location;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getCity() {
        return city;
    }

    public String getCooksCount() {
        return cooksCount;
    }

    public String getWaitersCount() {
        return waitersCount;
    }
}
