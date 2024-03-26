package com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.basicSettings.model;

import android.net.Uri;

public class SettingsUserModel {
    private String name;
    private String email;
    private Uri uri;

    public SettingsUserModel(String name, String email, Uri uri) {
        this.name = name;
        this.email = email;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Uri getUri() {
        return uri;
    }
}
