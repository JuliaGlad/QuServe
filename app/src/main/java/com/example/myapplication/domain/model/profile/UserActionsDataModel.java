package com.example.myapplication.domain.model.profile;

public class UserActionsDataModel {
    private final String ownQueue;
    private final String restaurantVisitor;
    private final String participateInQueue;

    public UserActionsDataModel(String ownQueue, String restaurantVisitor, String participateInQueue) {
        this.ownQueue = ownQueue;
        this.restaurantVisitor = restaurantVisitor;
        this.participateInQueue = participateInQueue;
    }

    public String getRestaurantVisitor() {
        return restaurantVisitor;
    }

    public String isOwnQueue() {
        return ownQueue;
    }

    public String isParticipateInQueue() {
        return participateInQueue;
    }
}
