package com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.categoryImageAddOwnPhoto;

import myapplication.android.ui.listeners.ButtonItemListener;

public class CategoryOwnImageModel {
    int id;
    int drawable;
    ButtonItemListener listener;

    public CategoryOwnImageModel(int id, int drawable, ButtonItemListener listener) {
        this.id = id;
        this.drawable = drawable;
        this.listener = listener;
    }
}
