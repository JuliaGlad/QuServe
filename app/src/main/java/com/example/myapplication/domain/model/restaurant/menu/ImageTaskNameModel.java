package com.example.myapplication.domain.model.restaurant.menu;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class ImageTaskNameModel {
    private final String name;
    private final Task<Uri> task;

    public ImageTaskNameModel(Task<Uri> task, String name) {
        this.task = task;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Task<Uri> getTask() {
        return task;
    }

}
