package com.example.myapplication.data.dto;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class ImageTaskNameDto {
    private final String name;
    private final Task<Uri> task;

    public ImageTaskNameDto(Task<Uri> task, String name) {
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
