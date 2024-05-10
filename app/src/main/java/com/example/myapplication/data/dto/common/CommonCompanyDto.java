package com.example.myapplication.data.dto.common;

public class CommonCompanyDto {
    private final String companyId;
    private final String name;
    private final String service;

    public CommonCompanyDto(String companyId, String name, String service) {
        this.companyId = companyId;
        this.service = service;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getService() {
        return service;
    }
}
