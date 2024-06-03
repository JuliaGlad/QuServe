package com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.models;

import com.example.myapplication.domain.model.profile.HistoryModel;

import java.util.List;

public class HistoryItemModel {
    private final String date;
    private final String service;
    private final String time;
    private final String name;

    public HistoryItemModel (String date, String service, String time, String name) {
        this.date = date;
        this.name = name;
        this.service = service;
        this.time = time;
    }

    public String getService() {
        return service;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }
}
