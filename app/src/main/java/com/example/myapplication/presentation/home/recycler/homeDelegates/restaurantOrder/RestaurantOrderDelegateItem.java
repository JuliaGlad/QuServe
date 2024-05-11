package com.example.myapplication.presentation.home.recycler.homeDelegates.restaurantOrder;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class RestaurantOrderDelegateItem implements DelegateItem<RestaurantOrderButtonModel> {

    RestaurantOrderButtonModel value;

    public RestaurantOrderDelegateItem(RestaurantOrderButtonModel value) {
        this.value = value;
    }

    @Override
    public RestaurantOrderButtonModel content() {
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
