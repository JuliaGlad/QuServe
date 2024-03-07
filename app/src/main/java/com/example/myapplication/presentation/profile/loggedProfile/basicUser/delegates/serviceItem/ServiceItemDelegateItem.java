package com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.serviceItem;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ServiceItemDelegateItem implements DelegateItem {

    private ServiceItemModel value;

    public ServiceItemDelegateItem(ServiceItemModel value) {
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
