package com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.topping;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ToppingDishDetailsDelegateItem implements DelegateItem<ToppingDishDetailsModel> {

    ToppingDishDetailsModel value;

    public ToppingDishDetailsDelegateItem(ToppingDishDetailsModel value) {
        this.value = value;
    }

    @Override
    public ToppingDishDetailsModel content() {
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
