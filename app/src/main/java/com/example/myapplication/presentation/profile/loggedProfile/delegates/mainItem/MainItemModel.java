package com.example.myapplication.presentation.profile.loggedProfile.delegates.mainItem;

import android.net.Uri;

public class MainItemModel {
    int id;
    Uri uri;
    String name;
    String email;
    String type;
    String companyId;

    public MainItemModel(int id, Uri uri, String name, String email, String type, String companyId) {
        this.id = id;
        this.uri = uri;
        this.name = name;
        this.email = email;
        this.type = type;
        this.companyId = companyId;
    }
}
