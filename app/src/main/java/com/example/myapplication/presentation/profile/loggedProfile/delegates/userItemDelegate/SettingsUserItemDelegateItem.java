package com.example.myapplication.presentation.profile.loggedProfile.delegates.userItemDelegate;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class SettingsUserItemDelegateItem implements DelegateItem {

    SettingsUserItemModel value;

    public SettingsUserItemDelegateItem(SettingsUserItemModel value) {
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
