package com.example.myapplication.data.dto;

import java.util.List;

public class CompanyDto {
    private final String id;
    private final String uri;
    private final String ownerId;
    private final String companyName;
    private final String companyEmail;
    private final String companyPhone;
    private final String companyService;

    public CompanyDto(String id, String ownerId, String uri, String companyName, String companyEmail, String companyPhone, String companyService) {
        this.id = id;
        this.ownerId = ownerId;
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.companyPhone = companyPhone;
        this.companyService = companyService;
        this.uri = uri;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getUri() {
        return uri;
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
