package com.example.myapplication.domain.model.common;

public class CommonCompanyModel {
    private final String companyId;
    private final String companyName;
    private final String service;

    public CommonCompanyModel(String companyId, String companyName, String service) {
        this.companyId = companyId;
        this.service = service;
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getService() {
        return service;
    }
}
