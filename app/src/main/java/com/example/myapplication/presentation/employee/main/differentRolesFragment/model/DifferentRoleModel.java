package com.example.myapplication.presentation.employee.main.differentRolesFragment.model;

public class DifferentRoleModel {
    private final String role;
    private final String companyId;
    private final String companyName;

    public DifferentRoleModel(String role, String companyId, String companyName) {
        this.role = role;
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getRole() {
        return role;
    }

    public String getCompanyName() {
        return companyName;
    }
}
