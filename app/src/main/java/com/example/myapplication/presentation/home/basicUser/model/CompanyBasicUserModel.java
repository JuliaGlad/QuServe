package com.example.myapplication.presentation.home.basicUser.model;

public class CompanyBasicUserModel {
    private final String id;
    private final String name;
    private final String service;

    public CompanyBasicUserModel(String id, String name, String service) {
        this.id = id;
        this.name = name;
        this.service = service;
    }

    public String getService() {
        return service;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
