package com.example.myapplication.data.dto;

import java.util.List;

public class CompanyDto {
    private final String id;
    private final String companyName;
    private final String companyEmail;
    private final String companyPhone;
    private final String companyService;

    public CompanyDto(String id, String companyName, String companyEmail, String companyPhone, String companyService) {
        this.id = id;
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.companyPhone = companyPhone;
        this.companyService = companyService;
    }

    public String getId(){
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public String getCompanyService() {
        return companyService;
    }
}