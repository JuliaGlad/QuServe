package com.example.myapplication.presentation.profile.loggedProfile.chooseCompany.companyDelegate;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class CompanyDelegateItem implements DelegateItem {

    CompanyModel value;

    public CompanyDelegateItem(CompanyModel value) {
        this.value = value;
    }

    @Override
    public Object content() {
        return value;
    }

    @Override
    public int id() {
        return value.hashCode();
    }

    @Override
    public boolean compareToOther(DelegateItem other) {
        return other.content() == value;
    }
}
