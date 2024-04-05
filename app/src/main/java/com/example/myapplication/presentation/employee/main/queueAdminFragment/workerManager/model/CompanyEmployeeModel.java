package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.model;

public class CompanyEmployeeModel {
    private final String name;
    private final String employeeId;
    private final String role;
    private final String count;

    public CompanyEmployeeModel(String name, String employeeId, String role, String count) {
        this.name = name;
        this.employeeId = employeeId;
        this.role = role;
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getRole() {
        return role;
    }
}
