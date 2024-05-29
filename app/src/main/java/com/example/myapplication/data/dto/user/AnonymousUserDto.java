package com.example.myapplication.data.dto.user;

public class AnonymousUserDto {
    private final String userId;
    private final String participateInQueue;
    private final String restaurantVisitor;


    public AnonymousUserDto(String userId, String participateInQueue, String restaurantVisitor) {
        this.userId = userId;
        this.participateInQueue = participateInQueue;
        this.restaurantVisitor = restaurantVisitor;
    }

    public String getUserId() {
        return userId;
    }

    public String getParticipateInQueue() {
        return participateInQueue;
    }

    public String getRestaurantVisitor() {
        return restaurantVisitor;
    }
}
