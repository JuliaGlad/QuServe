package com.example.myapplication.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "anonymousUser")
public class AnonymousUserEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    private String userId;
    private String participateInQueue;
    private String restaurantVisitor;

    public AnonymousUserEntity(@NonNull String userId, String participateInQueue, String restaurantVisitor) {
        this.userId = userId;
        this.participateInQueue = participateInQueue;
        this.restaurantVisitor = restaurantVisitor;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public String getParticipateInQueue() {
        return participateInQueue;
    }

    public String getRestaurantVisitor() {
        return restaurantVisitor;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public void setParticipateInQueue(String participateInQueue) {
        this.participateInQueue = participateInQueue;
    }

    public void setRestaurantVisitor(String restaurantVisitor) {
        this.restaurantVisitor = restaurantVisitor;
    }
}
