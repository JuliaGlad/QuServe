package com.example.myapplication.presentation.queue.participantList.statisticsDelegate;

import myapplication.android.ui.listeners.ButtonItemListener;

public class StatisticsModel {
    int id;
    ButtonItemListener listener;

    public StatisticsModel(int id, ButtonItemListener listener) {
        this.id = id;
        this.listener = listener;
    }

}
