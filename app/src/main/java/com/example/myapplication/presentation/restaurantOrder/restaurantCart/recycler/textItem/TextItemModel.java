package com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.textItem;

import myapplication.android.ui.listeners.ButtonItemListener;

public class TextItemModel {
    int id;
    String name;
    ButtonItemListener listener;

    public TextItemModel(int id, String name, ButtonItemListener listener) {
        this.id = id;
        this.name = name;
        this.listener = listener;
    }

    public boolean compareTo(TextItemModel other){
        return other.hashCode() == this.hashCode();
    }
}
