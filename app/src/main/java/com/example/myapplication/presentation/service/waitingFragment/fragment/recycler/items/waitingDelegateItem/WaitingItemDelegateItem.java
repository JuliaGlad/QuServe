package com.example.myapplication.presentation.service.waitingFragment.fragment.recycler.items.waitingDelegateItem;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class WaitingItemDelegateItem implements DelegateItem {

    WaitingItemModel value;

    public WaitingItemDelegateItem(WaitingItemModel value) {
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
