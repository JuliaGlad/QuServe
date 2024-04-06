package com.example.myapplication.domain.model.company;

public class EmployeeRoleCompanyModel {
    private final String companyId;
    private final String companyName;

    public EmployeeRoleCompanyModel(String companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

}
