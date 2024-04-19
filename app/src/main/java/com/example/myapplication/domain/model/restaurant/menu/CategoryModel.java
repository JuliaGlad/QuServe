package com.example.myapplication.domain.model.restaurant.menu;

public class CategoryModel {
    private final String categoryId;
    private final String name;

    public CategoryModel(String categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }
}
