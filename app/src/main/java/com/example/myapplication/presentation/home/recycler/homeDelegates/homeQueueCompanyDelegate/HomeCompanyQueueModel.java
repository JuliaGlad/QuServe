package com.example.myapplication.presentation.home.recycler.homeDelegates.homeQueueCompanyDelegate;

import myapplication.android.ui.listeners.ButtonItemListener;

public class HomeCompanyQueueModel {
    int id;
    String name;
    String peopleInQueue;
    ButtonItemListener listener;

    public HomeCompanyQueueModel(int id, String name, String peopleInQueue, ButtonItemListener listener) {
        this.id = id;
        this.name = name;
        this.peopleInQueue = peopleInQueue;
        this.listener = listener;
    }
}
