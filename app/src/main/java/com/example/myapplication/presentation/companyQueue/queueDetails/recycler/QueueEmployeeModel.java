package com.example.myapplication.presentation.companyQueue.queueDetails.recycler;

import android.net.Uri;

import myapplication.android.ui.listeners.ButtonItemListener;

public class QueueEmployeeModel {
    int id;
    String employeeId;
    String name;
    String role;
    ButtonItemListener listener;

    public QueueEmployeeModel(int id, String employeeId, String name, String role,ButtonItemListener listener) {
        this.name = name;
        this.role = role;
        this.id = id;
        this.employeeId = employeeId;
        this.listener = listener;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    boolean compareTo(QueueEmployeeModel other){
        return other.id == id;
    }
}
