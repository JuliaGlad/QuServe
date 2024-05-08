package com.example.myapplication.data.dto;

public class LocationDto {
    private final String locationId;
    private final String location;
    private final String city;
    private final String cooksCount;
    private final String waitersCount;
    private final String ordersCount;

    public LocationDto(String locationId, String location, String city, String cooksCount, String waitersCount, String ordersCount) {
        this.locationId = locationId;
        this.location = location;
        this.city = city;
        this.ordersCount = ordersCount;
        this.cooksCount = cooksCount;
        this.waitersCount = waitersCount;
    }

    public String getOrdersCount() {
        return ordersCount;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getLocation() {
        return location;
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
