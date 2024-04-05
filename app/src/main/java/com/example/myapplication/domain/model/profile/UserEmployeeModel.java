package com.example.myapplication.domain.model.profile;

public class UserEmployeeModel {
    private final String role;
    private final String companyId;

    public UserEmployeeModel(String role, String companyId) {
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
