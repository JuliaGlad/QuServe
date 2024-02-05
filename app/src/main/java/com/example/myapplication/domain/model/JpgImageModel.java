package com.example.myapplication.domain.model;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class JpgImageModel {

    private Task<Uri> imageUri;

    public JpgImageModel(Task<Uri> imageUri) {
        this.imageUri = imageUri;
    }

    public Task<Uri> getImageUri() {
        return imageUri;
    }
}
