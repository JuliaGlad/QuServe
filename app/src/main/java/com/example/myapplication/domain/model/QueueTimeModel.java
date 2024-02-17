package com.example.myapplication.domain.model;

public class QueueTimeModel {

    private String id;
    private String time;

    public QueueTimeModel(String id, String time) {
        this.id = id;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

}
