package com.example.myapplication.presentation.home.homeDelegates.button;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class HomeButtonDelegateItem implements DelegateItem<HomeButtonModel> {

    HomeButtonModel value;

    public HomeButtonDelegateItem(HomeButtonModel value) {
        this.value = value;
    }

    @Override
    public HomeButtonModel content() {
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
