package com.example.myapplication.presentation.home.basicUser.model;

import java.util.List;

public class HomeBasicUserModel {
    private final List<CompanyBasicUserModel> models;
    private final QueueBasicUserHomeModel restaurantVisitor;
    private final QueueBasicUserHomeModel participateQueue;
    private final QueueBasicUserHomeModel ownQueue;

    public HomeBasicUserModel(List<CompanyBasicUserModel> models, QueueBasicUserHomeModel restaurantVisitor, QueueBasicUserHomeModel participateQueue, QueueBasicUserHomeModel ownQueue) {
        this.models = models;
        this.restaurantVisitor = restaurantVisitor;
        this.participateQueue = participateQueue;
        this.ownQueue = ownQueue;
    }

    public QueueBasicUserHomeModel getRestaurantVisitor() {
        return restaurantVisitor;
    }

    public List<CompanyBasicUserModel> getModels() {
        return models;
    }

    public QueueBasicUserHomeModel getParticipateQueue() {
        return participateQueue;
    }

    public QueueBasicUserHomeModel getOwnQueue() {
        return ownQueue;
    }
}
