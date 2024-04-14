package com.example.myapplication.presentation.restaurantLocation.locations.recycler;

import myapplication.android.ui.listeners.ButtonItemListener;

public class RestaurantLocationModel {
    private final int id;
    private final String locationId;
    private final String location;
    private final String city;
    private final String waitersCount;
    private final String cookCount;
    private final ButtonItemListener listener;

    public RestaurantLocationModel(int id, String locationId, String location, String city, String waitersCount, String cookCount, ButtonItemListener listener) {
        this.id = id;
        this.locationId = locationId;
        this.location = location;
        this.city = city;
        this.waitersCount = waitersCount;
        this.cookCount = cookCount;
        this.listener = listener;
    }

    public boolean compareTo(RestaurantLocationModel other){
        return other.hashCode() == this.hashCode();
    }

    public ButtonItemListener getListener() {
        return listener;
    }

    public int getId() {
        return id;
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

    public String getWaitersCount() {
        return waitersCount;
    }

    public String getCookCount() {
        return cookCount;
    }
}
