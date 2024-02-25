package com.example.myapplication.domain.model;

public class UserBooleanDataModel {
    private final boolean ownQueue;
    private final boolean participateInQueue;

    public UserBooleanDataModel(boolean ownQueue, boolean participateInQueue) {
        this.ownQueue = ownQueue;
        this.participateInQueue = participateInQueue;
    }

    public boolean isOwnQueue() {
        return ownQueue;
    }

    public boolean isParticipateInQueue() {
        return participateInQueue;
    }
}
