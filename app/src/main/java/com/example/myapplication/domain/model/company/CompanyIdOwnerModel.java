package com.example.myapplication.domain.model.company;

public class CompanyIdOwnerModel {
    private final String ownerId;
    private final String companyId;

    public CompanyIdOwnerModel(String ownerId, String companyId) {
        this.ownerId = ownerId;
        this.companyId = companyId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getCompanyId() {
        return companyId;
    }
}
