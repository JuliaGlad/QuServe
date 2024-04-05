package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.recycler;

import myapplication.android.ui.listeners.ButtonItemListener;

public class WorkerManagerModel {
    private final int id;
    private final String userId;
    String name;
    String count;
    private final ButtonItemListener listener;

    public WorkerManagerModel(int id, String userId, String name, String count, ButtonItemListener listener) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.count = count;
        this.listener = listener;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getCount() {
        return count;
    }

    public ButtonItemListener getListener() {
        return listener;
    }

    public boolean compareTo(WorkerManagerModel other){
        return other.hashCode() == this.hashCode();
    }
}
