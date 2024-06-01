package com.example.myapplication.domain.model.restaurant.menu;

public class CategoryModel {
    private final String categoryId;
    private final String defaultImage;
    private final String name;

    public CategoryModel(String categoryId, String defaultImage, String name) {
        this.categoryId = categoryId;
        this.defaultImage = defaultImage;
        this.name = name;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }
}
