package com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.models;

public class EmployeeModel {
    String id;
    String name;
    String role;

    public EmployeeModel(String id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
