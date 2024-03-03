package com.example.myapplication.domain.model.company;

public class EmployeeMainModel {
    private String name;
    private String id;
    private String role;

    public  EmployeeMainModel(String name, String id, String role) {
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
