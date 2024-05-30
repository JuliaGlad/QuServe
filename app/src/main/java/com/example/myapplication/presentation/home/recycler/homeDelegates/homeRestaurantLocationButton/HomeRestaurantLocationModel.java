package com.example.myapplication.presentation.home.recycler.homeDelegates.homeRestaurantLocationButton;

import myapplication.android.ui.listeners.ButtonItemListener;

public class HomeRestaurantLocationModel {
    int id;
    String locationId;
    String location;
    String ordersCount;
    ButtonItemListener listener;

    public HomeRestaurantLocationModel(int id, String locationId, String location, String ordersCount, ButtonItemListener listener) {
        this.id = id;
        this.location = location;
        this.locationId = locationId;
        this.ordersCount = ordersCount;
        this.listener = listener;
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

    public String getOrdersCount() {
        return ordersCount;
    }
}
