package com.example.myapplication.domain.model.company;

public class CompanyForWorkerModel {
    private final String companyId;
    private final String companyName;

    public CompanyForWorkerModel(String companyId, String companyName) {
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
