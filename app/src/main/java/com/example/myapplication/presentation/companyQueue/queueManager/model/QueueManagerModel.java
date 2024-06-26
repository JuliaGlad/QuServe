package com.example.myapplication.presentation.companyQueue.queueManager.model;

public class QueueManagerModel {
    private final String name;
    private final String id;
    private final String location;
    private final String city;
    private final String workersCount;

    public QueueManagerModel(String name, String id, String location, String city, String workersCount) {
        this.name = name;
        this.id = id;
        this.location = location;
        this.city = city;
        this.workersCount = workersCount;
    }

    public String getCity() {
        return city;
    }

    public String getWorkersCount() {
        return workersCount;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
