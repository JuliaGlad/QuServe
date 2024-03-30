package com.example.myapplication.presentation.home.homeDelegates.actionButton;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class HomeActionButtonDelegateItem implements DelegateItem<HomeActionButtonModel> {

    HomeActionButtonModel value;

    public HomeActionButtonDelegateItem(HomeActionButtonModel value) {
        this.value = value;
    }

    @Override
    public HomeActionButtonModel content() {
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
