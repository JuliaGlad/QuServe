package com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.delegateRecycler;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class RecyclerDelegateItem implements DelegateItem {

    RecyclerModel value;

    public RecyclerDelegateItem(RecyclerModel value) {
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
