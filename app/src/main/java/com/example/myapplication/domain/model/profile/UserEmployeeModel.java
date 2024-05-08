package com.example.myapplication.domain.model.profile;

public class UserEmployeeModel {
    private final String role;
    private final String companyId;
    private final String locationId;

    public UserEmployeeModel(String role, String companyId, String locationId) {
        this.role = role;
        this.locationId = locationId;
        this.companyId = companyId;
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
