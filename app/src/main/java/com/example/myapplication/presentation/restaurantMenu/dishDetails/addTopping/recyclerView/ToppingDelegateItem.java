package com.example.myapplication.presentation.restaurantMenu.dishDetails.addTopping.recyclerView;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ToppingDelegateItem implements DelegateItem<ToppingModel> {

    ToppingModel value;

    public ToppingDelegateItem(ToppingModel value) {
        this.value = value;
    }

    @Override
    public ToppingModel content() {
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
