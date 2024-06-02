package com.example.myapplication.presentation.home.recycler.homeDelegates.homeQueueCompanyDelegate;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class HomeCompanyQueueDelegateItem implements DelegateItem {

    HomeCompanyQueueModel value;

    public HomeCompanyQueueDelegateItem(HomeCompanyQueueModel value) {
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
        return other.content().equals(content());
    }
}
