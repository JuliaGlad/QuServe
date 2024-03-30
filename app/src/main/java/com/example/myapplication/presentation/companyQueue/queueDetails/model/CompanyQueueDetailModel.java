package com.example.myapplication.presentation.companyQueue.queueDetails.model;

import android.net.Uri;

public class CompanyQueueDetailModel {

    private final String queueId;
    private final String name;
    private final Uri uri;

    public CompanyQueueDetailModel(String queueId, String name, Uri uri) {
        this.queueId = queueId;
        this.name = name;
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public String getQueueId() {
        return queueId;
    }

    public String getName() {
        return name;
    }
}
