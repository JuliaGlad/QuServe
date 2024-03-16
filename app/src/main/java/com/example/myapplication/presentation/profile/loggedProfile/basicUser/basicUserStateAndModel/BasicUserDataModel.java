package com.example.myapplication.presentation.profile.loggedProfile.basicUser.basicUserStateAndModel;

import android.net.Uri;

public class BasicUserDataModel {
    private final String name;
    private final String email;
    private final Uri uri;
    private final boolean aBoolean;

    public BasicUserDataModel(String name, String email, Uri uri, boolean aBoolean) {
        this.name = name;
        this.email = email;
        this.uri = uri;
        this.aBoolean = aBoolean;
    }

    public boolean isaBoolean() {
        return aBoolean;
    }

    public String getEmail() {
        return email;
    }

    public Uri getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }
}
