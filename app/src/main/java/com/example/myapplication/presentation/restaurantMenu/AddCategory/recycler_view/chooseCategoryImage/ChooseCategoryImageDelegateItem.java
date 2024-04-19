package com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.chooseCategoryImage;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ChooseCategoryImageDelegateItem implements DelegateItem<ChooseCategoryImageModel> {

    ChooseCategoryImageModel value;

    public ChooseCategoryImageDelegateItem(ChooseCategoryImageModel value) {
        this.value = value;
    }

    public ChooseCategoryImageModel getValue() {
        return value;
    }

    @Override
    public ChooseCategoryImageModel content() {
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
