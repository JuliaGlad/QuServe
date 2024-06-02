package com.example.myapplication.data.dto.user;

import java.util.List;

public class UserEmployeeRoleDto {
    private final String role;
    private final String companyId;
    private final String companyName;

    public UserEmployeeRoleDto(String role, String companyId, String companyName) {
        this.role = role;
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getRole() {
        return role;
    }
}
