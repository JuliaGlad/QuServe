package com.example.myapplication.domain.model.common;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class ImageModel {

    private final Uri imageUri;

    public ImageModel(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}
