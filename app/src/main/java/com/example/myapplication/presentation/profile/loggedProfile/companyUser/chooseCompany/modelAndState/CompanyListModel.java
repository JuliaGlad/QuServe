package com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany.modelAndState;

public class CompanyListModel {

    private final String id;
    private final String name;
    private final String service;

    public CompanyListModel(String id, String service, String name) {
        this.id = id;
        this.name = name;
        this.service = service;
    }

    public String getService() {
        return service;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

}
