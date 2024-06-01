package com.example.myapplication.presentation.restaurantMenu.model;

public class CategoryImageNameModel {
    private final String name;
    private final String defaultImage;

    public CategoryImageNameModel(String name, String defaultImage) {
        this.name = name;
        this.defaultImage = defaultImage;
    }

    public String getName() {
        return name;
    }

    public String getDefaultImage() {
        return defaultImage;
    }
}
