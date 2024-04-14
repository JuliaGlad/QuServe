package com.example.myapplication.presentation.restaurantMenu.model;

import android.net.Uri;

public class VariantsModel {
    private final String name;
    private final Uri uri;

    public VariantsModel(String name, Uri uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public Uri getUri() {
        return uri;
    }
}
