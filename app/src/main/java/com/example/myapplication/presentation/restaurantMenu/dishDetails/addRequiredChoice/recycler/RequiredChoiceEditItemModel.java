package com.example.myapplication.presentation.restaurantMenu.dishDetails.addRequiredChoice.recycler;

import myapplication.android.ui.listeners.ButtonItemListener;
import myapplication.android.ui.listeners.ResultListener;

public class RequiredChoiceEditItemModel {
    int id;
    ButtonItemListener listener;
    ResultListener<String> name;

    public RequiredChoiceEditItemModel(int id, ButtonItemListener listener, ResultListener<String> name) {
        this.id = id;
        this.name = name;
        this.listener = listener;
    }
}
