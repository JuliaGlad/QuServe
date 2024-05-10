package com.example.myapplication.data.dto.user;

import java.util.List;

public class UserEmployeeRoleDto {
    private final String role;
    private final String companyId;

    public UserEmployeeRoleDto(String role, String companyId) {
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
