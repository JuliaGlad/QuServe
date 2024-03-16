package com.example.myapplication.presentation.profile.loggedProfile.delegates.companyListItem;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class CompanyListItemDelegateItem implements DelegateItem {

    CompanyListItemDelegateModel value;

    public CompanyListItemDelegateItem(CompanyListItemDelegateModel value) {
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
        return other.content() == content();
    }
}
