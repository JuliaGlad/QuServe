package com.example.myapplication.domain.model.queue;

public class QueueNameModel {
    private String queueId;
    private String name;

    public QueueNameModel( String queueId, String name) {
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
