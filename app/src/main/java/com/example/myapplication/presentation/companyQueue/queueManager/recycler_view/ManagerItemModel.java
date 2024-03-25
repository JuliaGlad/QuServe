package com.example.myapplication.presentation.companyQueue.queueManager.recycler_view;

import myapplication.android.ui.listeners.ButtonItemListener;

public class ManagerItemModel {
    int id;
    String queueId;
    String queueName;
    String workers;
    String location;
    String city;
    ButtonItemListener listener;

    public ManagerItemModel(int id, String queueId, String queueName, String workers, String location, String city, ButtonItemListener listener) {
        this.id = id;
        this.queueId = queueId;
        this.queueName = queueName;
        this.workers = workers;
        this.location = location;
        this.city = city;
        this.listener = listener;
    }

    public String getCity() {
        return city;
    }

    public boolean compareToOther(ManagerItemModel other){
        return other.id == id;
    }
}
