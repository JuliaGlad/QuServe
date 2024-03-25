package com.example.myapplication.presentation.companyQueue.models;

public class EmployeeModel {

    private final String userId;
    private final String name;

    public EmployeeModel(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
