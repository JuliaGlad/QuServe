package com.example.myapplication.presentation.companyQueue.models;

public class EmployeeModel {

    private final String userId;
    private final String name;
    private final String queueCount;

    public EmployeeModel(String userId, String name, String queueCount) {
        this.userId = userId;
        this.name = name;
        this.queueCount = queueCount;
    }

    public String getQueueCount() {
        return queueCount;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
