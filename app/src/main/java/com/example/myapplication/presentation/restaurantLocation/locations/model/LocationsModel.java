package com.example.myapplication.presentation.restaurantLocation.locations.model;

public class LocationsModel {
    private final String locationId;
    private final String location;
    private final String city;
    private final String cooksCount;
    private final String waitersCount;

    public LocationsModel(String locationId, String location, String city, String cooksCount, String waitersCount) {
        this.locationId = locationId;
        this.location = location;
        this.city = city;
        this.cooksCount = cooksCount;
        this.waitersCount = waitersCount;
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
