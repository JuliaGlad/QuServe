package com.example.myapplication.domain.model.company;

public class CompanyNameAndEmailModel {
    private final String name;
    private final String email;

    public CompanyNameAndEmailModel(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

}
