package com.example.myapplication.data.dto;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class ImageDto {
    private Task<Uri> imageUri;

    public Task<Uri> getImageUri() {
        return imageUri;
    }

    public ImageDto(Task<Uri> imageUri) {
        this.imageUri = imageUri;
    }
}
