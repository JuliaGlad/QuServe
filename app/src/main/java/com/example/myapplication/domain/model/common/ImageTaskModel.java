package com.example.myapplication.domain.model.common;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class ImageTaskModel {

    private final Task<Uri> task;

    public ImageTaskModel(Task<Uri> task) {
        this.task = task;
    }

    public Task<Uri> getTask() {
        return task;
    }
}
