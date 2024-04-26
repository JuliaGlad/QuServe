package com.example.myapplication.presentation.restaurantOrder.dishDetails.recycler.requireChoice;

import myapplication.android.ui.listeners.ButtonItemListener;
import myapplication.android.ui.listeners.ButtonStringListener;

public class RequireChoiceOrderModel {
    int id;
    String name;
    boolean isDefault;
    String chosen;
    ButtonItemListener listener;

    public RequireChoiceOrderModel(int id, String name, boolean isDefault, String chosen, ButtonItemListener  listener) {
        this.id = id;
        this.name = name;
        this.isDefault = isDefault;
        this.chosen = chosen;
        this.listener = listener;
    }

    public boolean compareTo(RequireChoiceOrderModel other){
        return this.hashCode() == other.hashCode();
    }
}
