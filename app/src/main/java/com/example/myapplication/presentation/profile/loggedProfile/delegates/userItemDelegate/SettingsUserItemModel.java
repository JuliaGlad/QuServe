package com.example.myapplication.presentation.profile.loggedProfile.delegates.userItemDelegate;

import android.net.Uri;

public class SettingsUserItemModel {
    int id;
    String name;
    String email;
    Uri uri;

    public SettingsUserItemModel(int id, String name, String email, Uri uri) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.uri = uri;
    }
}
