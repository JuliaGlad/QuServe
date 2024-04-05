package com.example.myapplication.domain.model.company;

public class EmployeeMainModel {
    private final String name;
    private final String id;
    private final String role;
    private final String activeQueues;

    public  EmployeeMainModel(String name, String id, String role, String activeQueues) {
        this.name = name;
        this.id = id;
        this.role = role;
        this.activeQueues = activeQueues;
    }

    public String getActiveQueues() {
        return activeQueues;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
