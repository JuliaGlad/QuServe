package com.example.myapplication.presentation.profile.loggedProfile.basicUser.model;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class UserModel {
    private final String name;
    private final String email;
    private final Uri image;

    public UserModel(String name, String email, Uri image) {
        this.name = name;
        this.email = email;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Uri getImage() {
        return image;
    }
}