package com.example.myapplication.presentation.home.recycler.homeDelegates.homeQueueCompanyDelegate;

import myapplication.android.ui.listeners.ButtonItemListener;

public class HomeCompanyQueueModel {
    int id;
    String queueId;
    String name;
    String peopleInQueue;
    ButtonItemListener listener;

    public HomeCompanyQueueModel(int id, String queueId, String name, String peopleInQueue, ButtonItemListener listener) {
        this.id = id;
        this.name = name;
        this.queueId = queueId;
        this.peopleInQueue = peopleInQueue;
        this.listener = listener;
    }

    public String getQueueId() {
        return queueId;
    }
}
