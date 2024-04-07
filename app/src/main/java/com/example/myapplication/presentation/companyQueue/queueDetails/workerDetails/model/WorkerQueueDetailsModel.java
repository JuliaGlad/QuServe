package com.example.myapplication.presentation.companyQueue.queueDetails.workerDetails.model;

import android.net.Uri;

public class WorkerQueueDetailsModel {
    private final String name;
    private final boolean isPaused;
    private final Uri uri;

    public WorkerQueueDetailsModel(String name, Uri uri, boolean isPaused) {
        this.name = name;
        this.isPaused = isPaused;
        this.uri = uri;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public String getName() {
        return name;
    }

    public Uri getUri() {
        return uri;
    }
}
