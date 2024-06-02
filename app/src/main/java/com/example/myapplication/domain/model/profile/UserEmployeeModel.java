package com.example.myapplication.domain.model.profile;

public class UserEmployeeModel {
    private final String role;
    private final String companyId;
    private final String locationId;
    private final String companyName;

    public UserEmployeeModel(String role, String companyId, String locationId, String companyName) {
        this.role = role;
        this.locationId = locationId;
        this.companyId = companyId;
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
