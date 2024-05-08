package com.example.myapplication.presentation.home.basicUser.model;

public class HomeBasicUserActionModel {
    String isOwnQueue;
    String isRestaurantVisitor;
    String isParticipateInQueue;
    boolean isCompanyOwner;

    public HomeBasicUserActionModel(String isOwnQueue, String isParticipateInQueue, String isRestaurantVisitor, boolean isCompanyOwner) {
        this.isOwnQueue = isOwnQueue;
        this.isParticipateInQueue = isParticipateInQueue;
        this.isCompanyOwner = isCompanyOwner;
        this.isRestaurantVisitor = isRestaurantVisitor;
    }

    public String isRestaurantVisitor() {
        return isRestaurantVisitor;
    }

    public String isOwnQueue() {
        return isOwnQueue;
    }

    public String isParticipateInQueue() {
        return isParticipateInQueue;
    }

    public boolean isCompanyOwner() {
        return isCompanyOwner;
    }
}
