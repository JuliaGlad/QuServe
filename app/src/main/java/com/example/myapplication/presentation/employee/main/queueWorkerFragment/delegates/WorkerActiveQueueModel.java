package com.example.myapplication.presentation.employee.main.queueWorkerFragment.delegates;

import androidx.annotation.Nullable;

import myapplication.android.ui.listeners.ButtonItemListener;

public class WorkerActiveQueueModel {
    private final int id;
    private final String name;
    private final String location;
    private final ButtonItemListener listener;

    public WorkerActiveQueueModel(int id, String name, String location, ButtonItemListener listener) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.listener = listener;
    }

    public ButtonItemListener getListener() {
        return listener;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getId() {
        return id;
    }

    public boolean compareTo(WorkerActiveQueueModel other) {
        return other.hashCode() == this.hashCode();
    }
}
