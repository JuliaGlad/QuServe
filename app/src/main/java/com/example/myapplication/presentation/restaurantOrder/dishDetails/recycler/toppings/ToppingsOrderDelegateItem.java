package com.example.myapplication.presentation.restaurantOrder.dishDetails.recycler.toppings;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ToppingsOrderDelegateItem implements DelegateItem<ToppingsOrderModel> {

    ToppingsOrderModel value;

    public ToppingsOrderDelegateItem(ToppingsOrderModel value) {
        this.value = value;
    }

    @Override
    public ToppingsOrderModel content() {
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
