package com.example.myapplication.presentation.restaurantOrder.dishDetails.recycler.requireChoice;

import myapplication.android.ui.listeners.ButtonItemListener;
import myapplication.android.ui.listeners.ButtonStringListener;

public class RequireChoiceOrderModel {
    int id;
    String name;
    boolean isDefault;
    boolean isChosen;
    ButtonItemListener listener;

    public RequireChoiceOrderModel(int id, String name, boolean isDefault, boolean isChosen, ButtonItemListener  listener) {
        this.id = id;
        this.name = name;
        this.isDefault = isDefault;
        this.isChosen = isChosen;
        this.listener = listener;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public boolean compareTo(RequireChoiceOrderModel other){
        return this.hashCode() == other.hashCode();
    }
}
