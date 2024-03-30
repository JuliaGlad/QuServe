package com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.models;

import java.util.List;

public class EditQueueModel {
    private final List<EmployeeModel> models;
    private final String name;
    private final String location;

    public EditQueueModel(String name, String location, List<EmployeeModel> models) {
        this.name = name;
        this.models = models;
        this.location = location;
    }

    public List<EmployeeModel> getModels() {
        return models;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}
