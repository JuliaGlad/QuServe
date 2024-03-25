package com.example.myapplication.domain.model.profile;

public class HistoryQueueModel {
    String time;
    String name;

    public HistoryQueueModel(String time, String name) {
        this.time = time;
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
}
