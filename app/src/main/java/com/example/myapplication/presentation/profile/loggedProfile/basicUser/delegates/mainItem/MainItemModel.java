package com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.mainItem;

import android.net.Uri;

public class MainItemModel {
    int id;
    Uri uri;
    String name;
    String email;

    public MainItemModel(int id, Uri uri, String name, String email) {
        this.id = id;
        this.uri = uri;
        this.name = name;
        this.email = email;
    }
}
