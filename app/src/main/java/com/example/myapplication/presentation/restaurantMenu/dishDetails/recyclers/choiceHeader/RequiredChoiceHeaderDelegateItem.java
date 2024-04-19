package com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.choiceHeader;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class RequiredChoiceHeaderDelegateItem implements DelegateItem<RequiredChoiceHeaderModel> {

    RequiredChoiceHeaderModel value;

    public RequiredChoiceHeaderDelegateItem(RequiredChoiceHeaderModel value) {
        this.value = value;
    }

    @Override
    public RequiredChoiceHeaderModel content() {
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
