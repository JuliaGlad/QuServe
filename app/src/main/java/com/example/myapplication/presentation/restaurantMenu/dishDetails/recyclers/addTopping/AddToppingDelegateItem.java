package com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.addTopping;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class AddToppingDelegateItem implements DelegateItem<AddToppingModel> {

    AddToppingModel value;

    public AddToppingDelegateItem(AddToppingModel value) {
        this.value = value;
    }

    @Override
    public AddToppingModel content() {
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
