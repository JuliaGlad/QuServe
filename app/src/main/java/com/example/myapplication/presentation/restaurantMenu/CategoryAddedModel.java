package com.example.myapplication.presentation.restaurantMenu;

import android.net.Uri;

public class CategoryAddedModel {
    private final Uri uri;
    private final String image;
    private final String id;
    private final String name;

    public CategoryAddedModel(String id, String image, Uri uri, String name) {
        this.uri = uri;
        this.id = id;
        this.image = image;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public Uri getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }
}
