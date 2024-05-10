package com.example.myapplication.presentation.common.waitingInQueue.recycler.items.leaveQueueItem;

import myapplication.android.ui.listeners.ButtonItemListener;

public class LeaveQueueModel {
    int id;
    ButtonItemListener listener;

    public LeaveQueueModel(int id, ButtonItemListener listener) {
        this.id = id;
        this.listener = listener;
    }
}
