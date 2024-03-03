package com.example.myapplication.domain.model.queue;

public class QueueInProgressModel {
    private final String id;
    private final String inProgress;

    public QueueInProgressModel(String id, String inProgress) {
        this.id = id;
        this.inProgress = inProgress;
    }

    public String getId() {
        return id;
    }

    public String getInProgress() {
        return inProgress;
    }
}
