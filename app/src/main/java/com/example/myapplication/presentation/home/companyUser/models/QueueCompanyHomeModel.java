package com.example.myapplication.presentation.home.companyUser.models;

public class QueueCompanyHomeModel {
    private final String queueId;
    private final String name;
    private final int participantsSize;

    public QueueCompanyHomeModel(String queueId, String name, int participantsSize) {
        this.queueId = queueId;
        this.name = name;
        this.participantsSize = participantsSize;
    }

    public String getQueueId() {
        return queueId;
    }

    public String getName() {
        return name;
    }

    public int getParticipantsSize() {
        return participantsSize;
    }
}
