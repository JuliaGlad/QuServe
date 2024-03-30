package com.example.myapplication.domain.model.company;

public class CompanyQueueParticipantsSizeAndNameModel {
    private final String queueId;
    private final int participantsSize;
    private final String name;

    public CompanyQueueParticipantsSizeAndNameModel(String queueId, int participantsSize, String name) {
        this.queueId = queueId;
        this.participantsSize = participantsSize;
        this.name = name;
    }

    public String getQueueId() {
        return queueId;
    }

    public int getParticipantsSize() {
        return participantsSize;
    }

    public String getName() {
        return name;
    }
}
