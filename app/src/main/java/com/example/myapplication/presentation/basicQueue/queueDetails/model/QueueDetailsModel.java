package com.example.myapplication.presentation.basicQueue.queueDetails.model;

import android.net.Uri;

public class QueueDetailsModel {
    private final String name;
    private final String id;
    private final Uri uri;

    public QueueDetailsModel(String name, String id, Uri uri) {
        this.name = name;
        this.id = id;
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
