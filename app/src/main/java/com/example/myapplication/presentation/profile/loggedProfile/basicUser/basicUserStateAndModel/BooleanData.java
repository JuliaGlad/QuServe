package com.example.myapplication.presentation.profile.loggedProfile.basicUser.basicUserStateAndModel;

import com.example.myapplication.domain.model.company.CompanyNameIdModel;

import java.util.List;

public class BooleanData {
    private boolean aBoolean;
    private List<CompanyNameIdModel> list;

    public BooleanData(boolean aBoolean, List<CompanyNameIdModel> list) {
        this.aBoolean = aBoolean;
        this.list = list;
    }

    public boolean isaBoolean() {
        return aBoolean;
    }

    public List<CompanyNameIdModel> getList() {
        return list;
    }
}
