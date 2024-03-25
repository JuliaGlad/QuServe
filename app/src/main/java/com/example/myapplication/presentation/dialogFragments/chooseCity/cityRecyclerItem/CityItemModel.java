package com.example.myapplication.presentation.dialogFragments.chooseCity.cityRecyclerItem;

import myapplication.android.ui.listeners.ButtonItemListener;

public class CityItemModel {

    int id;
    String name;
    ButtonItemListener listener;

    public CityItemModel(int id, String name, ButtonItemListener listener) {
        this.id = id;
        this.name = name;
        this.listener = listener;
    }

    public String getName() {
        return name;
    }

    public boolean compareTo(CityItemModel other){
        return other.id == id;
    }
}
