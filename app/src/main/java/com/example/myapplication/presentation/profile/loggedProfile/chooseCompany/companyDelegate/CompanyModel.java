package com.example.myapplication.presentation.profile.loggedProfile.chooseCompany.companyDelegate;

import myapplication.android.ui.listeners.ButtonItemListener;

public class CompanyModel {
    int id;
    String name;
    String companyId;
    ButtonItemListener listener;

    public CompanyModel(int id, String name, String companyId, ButtonItemListener listener) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.listener = listener;
    }

    public int getId(){
        return id;
    }

    public ButtonItemListener getListener(){
        return listener;
    }

    public String getName() {
        return name;
    }

    public String getCompanyId() {
        return companyId;
    }
}
