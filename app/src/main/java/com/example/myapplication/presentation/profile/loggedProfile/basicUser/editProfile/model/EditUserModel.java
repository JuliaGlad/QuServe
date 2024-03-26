package com.example.myapplication.presentation.profile.loggedProfile.basicUser.editProfile.model;

import android.net.Uri;

public class EditUserModel {

    private final String phoneNumber;
    private final String name;
    private final String birthday;
    private final String email;
    private final String gender;
    private final Uri uri;

    public EditUserModel(String phoneNumber, String name, String birthday, String email, String gender, Uri uri) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.gender = gender;
        this.uri = uri;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public Uri getUri() {
        return uri;
    }
}
