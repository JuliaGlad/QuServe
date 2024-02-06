package com.example.myapplication.domain.model;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class ImageModel {

    private Task<Uri> imageUri;

    public ImageModel(Task<Uri> imageUri) {
        this.imageUri = imageUri;
    }

    public Task<Uri> getImageUri() {
        return imageUri;
    }
}
