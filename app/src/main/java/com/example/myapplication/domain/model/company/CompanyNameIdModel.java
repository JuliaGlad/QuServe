package com.example.myapplication.domain.model.company;

public class CompanyNameIdModel {
    private final String id;
    private final String name;

    public CompanyNameIdModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
