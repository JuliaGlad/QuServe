package com.example.myapplication.data.dto.restaurant;

public class CategoryDto {
    private final String categoryId;
    private final String defaultImage;
    private final String name;

    public CategoryDto(String categoryId, String defaultImage, String name) {
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
