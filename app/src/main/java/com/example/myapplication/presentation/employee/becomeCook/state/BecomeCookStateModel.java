package com.example.myapplication.presentation.employee.becomeCook.state;

import android.net.Uri;

public class BecomeCookStateModel {
    private final String name;
    private final String restaurantId;
    private final Uri uri;

    public BecomeCookStateModel(String name, String restaurantId, Uri uri) {
        this.name = name;
        this.restaurantId = restaurantId;
        this.uri = uri;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public Uri getUri() {
        return uri;
    }
}
