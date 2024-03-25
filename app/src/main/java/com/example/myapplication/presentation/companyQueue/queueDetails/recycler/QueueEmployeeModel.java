package com.example.myapplication.presentation.companyQueue.queueDetails.recycler;

import android.net.Uri;

public class QueueEmployeeModel {
    int id;
    String employeeId;
    String name;
    String role;
    Uri uri;

    public QueueEmployeeModel(int id, String employeeId, String name, String role, Uri uri) {
        this.name = name;
        this.role = role;
        this.uri = uri;
        this.id = id;
        this.employeeId = employeeId;
    }

    boolean compareTo(QueueEmployeeModel other){
        return other.id == id;
    }
}
