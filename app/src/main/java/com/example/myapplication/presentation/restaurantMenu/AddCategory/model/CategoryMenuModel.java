package com.example.myapplication.presentation.restaurantMenu.AddCategory.model;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class CategoryMenuModel {
    private final String categoryId;
    private final String name;
    private final Task<Uri> task;

    public CategoryMenuModel(String categoryId, String name, Task<Uri> task) {
        this.categoryId = categoryId;
        this.name = name;
        this.task = task;
    }

    public Task<Uri> getTask() {
        return task;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }
}
