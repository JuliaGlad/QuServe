package com.example.myapplication.presentation.restaurantMenu.dishDetails.addRequiredChoice.recycler;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class RequiredChoiceEditDelegateItem implements DelegateItem<RequiredChoiceEditItemModel> {

    RequiredChoiceEditItemModel value;

    public RequiredChoiceEditDelegateItem(RequiredChoiceEditItemModel value) {
        this.value = value;
    }

    @Override
    public RequiredChoiceEditItemModel content() {
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
