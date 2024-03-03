package com.example.myapplication.data.dto;

public class EmployeeDto {
    private String name;
    private String id;
    private String role;

    public EmployeeDto(String name, String id, String role) {
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
