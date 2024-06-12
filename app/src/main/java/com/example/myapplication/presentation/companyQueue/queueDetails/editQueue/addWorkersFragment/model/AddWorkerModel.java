package com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.model;

public class AddWorkerModel {
    private final String name;
    private final String id;
    private final String role;
    private final String activeQueuesCount;

    public AddWorkerModel(String name, String id, String role, String activeQueuesCount) {
        this.name = name;
        this.id = id;
        this.role = role;
        this.activeQueuesCount = activeQueuesCount;
    }

    public String getActiveQueuesCount() {
        return activeQueuesCount;
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
