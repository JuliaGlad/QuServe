package com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers.model;

public class EmployeeStateModel {

    private final String name;
    private final String id;
    private final String role;
    private final String queueCount;

    public EmployeeStateModel(String name, String id, String role, String queueCount) {
        this.name = name;
        this.id = id;
        this.role = role;
        this.queueCount = queueCount;
    }

    public String getQueueCount() {
        return queueCount;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
