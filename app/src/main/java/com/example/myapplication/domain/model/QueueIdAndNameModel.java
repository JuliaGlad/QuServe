package com.example.myapplication.domain.model;

public class QueueIdAndNameModel {

    private String id;
    private String name;

    public QueueIdAndNameModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
