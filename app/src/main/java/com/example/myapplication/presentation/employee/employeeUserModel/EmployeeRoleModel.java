package com.example.myapplication.presentation.employee.employeeUserModel;

public class EmployeeRoleModel {
    private final String role;
    private final String companyId;
    private final String locationId;

    public EmployeeRoleModel(String role, String companyId, String locationId) {
        this.role = role;
        this.companyId = companyId;
        this.locationId = locationId;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getRole() {
        return role;
    }
}
