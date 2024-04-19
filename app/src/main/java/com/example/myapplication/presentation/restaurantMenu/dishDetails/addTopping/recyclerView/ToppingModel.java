package com.example.myapplication.presentation.restaurantMenu.dishDetails.addTopping.recyclerView;

import android.net.Uri;

import myapplication.android.ui.listeners.ButtonItemListener;

public class ToppingModel {
    int id;
    String name;
    String price;
    Uri uri;
    ButtonItemListener listener;

    public ToppingModel(int id, String name, String price, Uri uri, ButtonItemListener listener) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.uri = uri;
        this.listener = listener;
    }
}
