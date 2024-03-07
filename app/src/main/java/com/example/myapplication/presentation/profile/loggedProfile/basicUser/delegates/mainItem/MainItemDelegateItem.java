package com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.mainItem;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class MainItemDelegateItem implements DelegateItem {

    private MainItemModel value;

    public MainItemDelegateItem(MainItemModel value) {
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
