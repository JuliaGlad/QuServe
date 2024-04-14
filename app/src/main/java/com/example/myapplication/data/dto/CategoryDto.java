package com.example.myapplication.data.dto;

public class CategoryDto {
    private final String categoryId;
    private final String name;

    public CategoryDto(String categoryId, String name) {
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
