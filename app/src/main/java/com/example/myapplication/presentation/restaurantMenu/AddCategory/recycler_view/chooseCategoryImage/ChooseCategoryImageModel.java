package com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.chooseCategoryImage;


import android.net.Uri;

import java.util.List;

import myapplication.android.ui.listeners.ButtonItemListener;

public class ChooseCategoryImageModel {
    int id;
    List<Integer> drawables;
    String name;
    Uri uri;
    ButtonItemListener listener;

    public ChooseCategoryImageModel(int id, List<Integer> drawables,String name, Uri uri, ButtonItemListener listener) {
        this.id = id;
        this.name = name;
        this.drawables = drawables;
        this.uri = uri;
        this.listener = listener;
    }

}
