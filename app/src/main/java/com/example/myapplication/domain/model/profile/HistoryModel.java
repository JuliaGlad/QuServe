package com.example.myapplication.domain.model.profile;

public class HistoryModel {
    private final String date;
    private final String service;
    private final String time;
    private final String name;

    public HistoryModel(String date, String service, String time, String name) {
        this.date = date;
        this.service = service;
        this.name = name;
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

