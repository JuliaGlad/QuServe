package com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.serviceRedItem;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ServiceRedItemDelegateItem implements DelegateItem {

    private ServiceRedItemModel value;

    public ServiceRedItemDelegateItem(ServiceRedItemModel value) {
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
