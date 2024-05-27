package com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.requiredChoice;

import myapplication.android.ui.listeners.ButtonItemListener;

public class RequiredChoiceItemModel {
    int id;
    String name;
    boolean isChosen;
    ButtonItemListener listener;

    public RequiredChoiceItemModel(int id, String name, boolean isChosen, ButtonItemListener listener) {
        this.id = id;
        this.name = name;
        this.isChosen = isChosen;
        this.listener = listener;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    public boolean compareTo(Object other){
        return this.hashCode() == other.hashCode();
    }
}
