package com.example.myapplication.presentation.profile.loggedProfile.companyUser.model;

import android.net.Uri;

public class CompanyUserModel {
    private final String name;
    private final String email;
    private final Uri Uri;

    public CompanyUserModel(String name, String email, Uri uri) {
        this.name = name;
        this.email = email;
        Uri = uri;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Uri getUri() {
        return Uri;
    }
}
