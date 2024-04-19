package com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.choiceHeader;

import myapplication.android.ui.listeners.ButtonItemListener;

public class RequiredChoiceHeaderModel {
    int id;
    String name;
    ButtonItemListener listener;

    public RequiredChoiceHeaderModel(int id, String name, ButtonItemListener listener) {
        this.id = id;
        this.name = name;
        this.listener = listener;
    }
}
