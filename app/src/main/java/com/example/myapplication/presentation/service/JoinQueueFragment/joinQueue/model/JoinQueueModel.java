package com.example.myapplication.presentation.service.JoinQueueFragment.joinQueue.model;

import android.net.Uri;

public class JoinQueueModel {
    private final String name;
    private final Uri uri;

    public JoinQueueModel(String name, Uri uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public Uri getUri() {
        return uri;
    }
}
