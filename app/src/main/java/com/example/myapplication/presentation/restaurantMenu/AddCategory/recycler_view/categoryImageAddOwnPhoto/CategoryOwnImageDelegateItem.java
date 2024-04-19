package com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.categoryImageAddOwnPhoto;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class CategoryOwnImageDelegateItem implements DelegateItem<CategoryOwnImageModel> {

    CategoryOwnImageModel value;

    public CategoryOwnImageDelegateItem(CategoryOwnImageModel value) {
        this.value = value;
    }

    @Override
    public CategoryOwnImageModel content() {
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
