package com.example.myapplication.data.dto.common;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class ImageDto {
    private final Uri imageUri;

    public Uri getImageUri() {
        return imageUri;
    }

    public ImageDto(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
