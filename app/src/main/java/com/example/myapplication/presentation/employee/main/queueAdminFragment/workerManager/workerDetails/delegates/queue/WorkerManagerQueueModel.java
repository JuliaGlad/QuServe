package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.workerDetails.delegates.queue;

public class WorkerManagerQueueModel {
    private final int id;
    private final String name;
    private final String location;

    public WorkerManagerQueueModel(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}
