package com.example.myapplication.presentation.restaurantOrder.dishDetails.recycler.requiredChoiceHeader;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class RequiredChoiceOrderHeaderDelegateItem implements DelegateItem<RequiredChoiceOrderHeaderModel> {

    RequiredChoiceOrderHeaderModel value;

    public RequiredChoiceOrderHeaderDelegateItem(RequiredChoiceOrderHeaderModel value) {
        this.value = value;
    }

    @Override
    public RequiredChoiceOrderHeaderModel content() {
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
