package com.example.myapplication.presentation.restaurantMenu.addDish.recycler;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class NumberPickerDelegateItem implements DelegateItem<NumberPickerModel> {

    NumberPickerModel value;

    public NumberPickerDelegateItem(NumberPickerModel value) {
        this.value = value;
    }

    @Override
    public NumberPickerModel content() {
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
