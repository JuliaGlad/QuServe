package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.addQueue.model;

public class AddQueueModel {
    private final String queueId;
    private final String location;
    private final String name;
    private final String city;
    private final String workersCount;

    public AddQueueModel(String queueId, String location, String name, String city, String workersCount) {
        this.queueId = queueId;
        this.location = location;
        this.name = name;
        this.city = city;
        this.workersCount = workersCount;
    }

    public String getQueueId() {
        return queueId;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getWorkersCount() {
        return workersCount;
    }
}
