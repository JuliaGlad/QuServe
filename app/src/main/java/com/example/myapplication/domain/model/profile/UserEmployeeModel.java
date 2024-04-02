package com.example.myapplication.domain.model.profile;

public class UserEmployeeModel {
    private final String role;
    private final String companyId;
    private final String companyPath;

    public UserEmployeeModel(String role, String companyId, String companyPath) {
        this.role = role;
        this.companyId = companyId;
        this.companyPath = companyPath;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getCompanyPath() {
        return companyPath;
    }

    public String getRole() {
        return role;
    }

}
