package com.example.myapplication.domain.model.company;

public class CompanyNameIdModel {
    private final String id;
    private final String name;
    private final String uri;

    public CompanyNameIdModel(String id, String name, String uri) {
        this.id = id;
        this.name = name;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
