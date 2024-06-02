package com.example.myapplication.presentation.employee.employeeUserModel;

public class EmployeeRoleModel {
    private final String role;
    private final String companyId;
    private final String locationId;
    private final String companyName;

    public EmployeeRoleModel(String role, String companyId, String locationId, String companyName) {
        this.role = role;
        this.companyId = companyId;
        this.locationId = locationId;
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
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
