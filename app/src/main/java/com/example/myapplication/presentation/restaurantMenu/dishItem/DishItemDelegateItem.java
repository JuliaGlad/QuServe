package com.example.myapplication.presentation.restaurantMenu.dishItem;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class DishItemDelegateItem implements DelegateItem<DishItemModel> {

    DishItemModel value;

    public DishItemDelegateItem(DishItemModel value) {
        this.value = value;
    }

    @Override
    public DishItemModel content() {
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
