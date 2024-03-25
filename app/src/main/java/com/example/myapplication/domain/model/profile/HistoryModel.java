package com.example.myapplication.domain.model.profile;

import java.util.List;

public class HistoryModel {
    private final String date;
    private final String time;
    private final String name;

    public HistoryModel(String date,String time, String name) {
        this.date = date;
        this.name = name;
        this.time = time;
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

