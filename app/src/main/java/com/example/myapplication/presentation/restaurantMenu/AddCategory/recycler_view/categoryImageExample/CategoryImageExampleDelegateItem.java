package com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.categoryImageExample;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class CategoryImageExampleDelegateItem implements DelegateItem<CategoryImageExampleModel> {

    CategoryImageExampleModel value;

    public CategoryImageExampleDelegateItem(CategoryImageExampleModel value) {
        this.value = value;
    }

    @Override
    public CategoryImageExampleModel content() {
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
