package com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.addTopping;

import myapplication.android.ui.listeners.ButtonItemListener;

public class AddToppingModel {
    int id;
    ButtonItemListener listener;

    public AddToppingModel(int id, ButtonItemListener listener) {
        this.id = id;
        this.listener = listener;
    }
}
