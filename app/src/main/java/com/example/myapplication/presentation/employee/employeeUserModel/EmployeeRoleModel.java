package com.example.myapplication.presentation.employee.employeeUserModel;

public class EmployeeRoleModel {
    private final String role;
    private final String companyId;

    public EmployeeRoleModel(String role, String companyId) {
        this.role = role;
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getRole() {
        return role;
    }
}
