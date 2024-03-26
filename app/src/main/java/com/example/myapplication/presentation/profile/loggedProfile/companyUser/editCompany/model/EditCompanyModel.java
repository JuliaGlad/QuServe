package com.example.myapplication.presentation.profile.loggedProfile.companyUser.editCompany.model;

import android.net.Uri;

public class EditCompanyModel {
    private final String name;
    private final String email;
    private final String phone;
    private final String service;
    private final Uri uri;

    public EditCompanyModel(String name, String email, String phone, String service, Uri uri) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.service = service;
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getService() {
        return service;
    }
}
