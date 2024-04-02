package com.example.myapplication.data.dto;

import java.util.List;

public class UserEmployeeRoleDto {
    private final String role;
    private final String companyId;
    private final String companyPath;

    public UserEmployeeRoleDto(String role, String companyId, String companyPath) {
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
