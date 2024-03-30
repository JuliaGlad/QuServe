package com.example.myapplication.presentation.home.basicUser.model;

public class CompanyBasicUserModel {
    private final String id;
    private final String name;

    public CompanyBasicUserModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
