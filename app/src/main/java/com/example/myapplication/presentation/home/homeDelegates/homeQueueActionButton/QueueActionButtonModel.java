package com.example.myapplication.presentation.home.homeDelegates.homeQueueActionButton;

import myapplication.android.ui.listeners.ButtonItemListener;

public class QueueActionButtonModel {
    int id;
    String name;
    String peopleInQueue;
    ButtonItemListener listener;

    public QueueActionButtonModel(int id, String name, String peopleInQueue, ButtonItemListener listener) {
        this.id = id;
        this.name = name;
        this.peopleInQueue = peopleInQueue;
        this.listener = listener;
    }

}
