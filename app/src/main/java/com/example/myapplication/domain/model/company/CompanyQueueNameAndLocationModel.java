package com.example.myapplication.domain.model.company;

public class CompanyQueueNameAndLocationModel {

    private final String name;
    private final String location;

    public CompanyQueueNameAndLocationModel(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}
