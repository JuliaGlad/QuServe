package com.example.myapplication.domain.model.company;

public class CompanyQueueNameModel {

    private final String queueId;
    private final String name;

    public CompanyQueueNameModel(String queueId, String name) {
        this.queueId = queueId;
        this.name = name;
    }

    public String getQueueId() {
        return queueId;
    }

    public String getName() {
        return name;
    }
}
