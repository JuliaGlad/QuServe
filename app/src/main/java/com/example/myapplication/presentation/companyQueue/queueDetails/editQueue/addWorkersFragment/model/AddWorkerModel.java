package com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.model;

public class AddWorkerModel {
    private final String name;
    private final String id;
    private final String role;

    public AddWorkerModel(String name, String id, String role) {
        this.name = name;
        this.id = id;
        this.role = role;
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
