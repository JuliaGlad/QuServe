package com.example.myapplication.presentation.home.basicUser.model;

public class HomeBasicUserBooleanModel {
    boolean isOwnQueue;
    boolean isParticipateInQueue;
    boolean isCompanyOwner;

    public HomeBasicUserBooleanModel(boolean isOwnQueue, boolean isParticipateInQueue, boolean isCompanyOwner) {
        this.isOwnQueue = isOwnQueue;
        this.isParticipateInQueue = isParticipateInQueue;
        this.isCompanyOwner = isCompanyOwner;
    }

    public boolean isOwnQueue() {
        return isOwnQueue;
    }

    public boolean isParticipateInQueue() {
        return isParticipateInQueue;
    }

    public boolean isCompanyOwner() {
        return isCompanyOwner;
    }
}
