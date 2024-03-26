package com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany.modelAndState;

public class CompanyListModel {

    private final String id;
    private final String name;
    private final String uri;

    public CompanyListModel(String id, String name, String uri) {
        this.id = id;
        this.name = name;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

}
