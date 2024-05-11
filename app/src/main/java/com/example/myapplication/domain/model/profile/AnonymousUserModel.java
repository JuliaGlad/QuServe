package com.example.myapplication.domain.model.profile;

public class AnonymousUserModel {
    private final String restaurantVisitor;
    private final String queueParticipant;

    public AnonymousUserModel(String restaurantVisitor, String queueParticipant) {
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
