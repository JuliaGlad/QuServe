package com.example.myapplication.domain.model.company;

public class WorkerModel {
    private final String name;
    private final String id;

    public WorkerModel(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
