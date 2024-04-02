package com.example.myapplication.domain.model.profile;

public class ActiveQueueEmployeeModel {
    private final String queueId;
    private final String queueName;
    private final String location;

    public ActiveQueueEmployeeModel(String queueId, String queueName, String location) {
        this.queueId = queueId;
        this.queueName = queueName;
        this.location = location;
    }

    public String getQueueId() {
        return queueId;
    }

    public String getQueueName() {
        return queueName;
    }

    public String getLocation() {
        return location;
    }
}
