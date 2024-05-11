package com.example.myapplication.presentation.home.anonymousUser.models;

public class AnonymousUserActionsHomeModel {
    private final String restaurantVisitor;
    private final String queueParticipant;

    public AnonymousUserActionsHomeModel(String restaurantVisitor, String queueParticipant) {
        this.restaurantVisitor = restaurantVisitor;
        this.queueParticipant = queueParticipant;
    }

    public String getRestaurantVisitor() {
        return restaurantVisitor;
    }

    public String getQueueParticipant() {
        return queueParticipant;
    }
}
