package com.example.myapplication.presentation.profile.loggedProfile.basicUser.basicUserStateAndModel;

import com.example.myapplication.domain.model.common.CommonCompanyModel;
import com.example.myapplication.domain.model.company.CompanyNameIdModel;

import java.util.List;

public class BooleanData {
    private boolean aBoolean;
    private List<CommonCompanyModel> list;

    public BooleanData(boolean aBoolean, List<CommonCompanyModel> list) {
        this.aBoolean = aBoolean;
        this.list = list;
    }

    public boolean isaBoolean() {
        return aBoolean;
    }

    public List<CommonCompanyModel> getList() {
        return list;
    }
}
