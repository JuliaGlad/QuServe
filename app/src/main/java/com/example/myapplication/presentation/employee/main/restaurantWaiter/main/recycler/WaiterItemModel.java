package com.example.myapplication.presentation.employee.main.restaurantWaiter.main.recycler;

import myapplication.android.ui.listeners.ButtonItemListener;

public class WaiterItemModel {
    int id;
    String tableNumber;
    String name;
    String count;
    ButtonItemListener listener;

    public WaiterItemModel(int id, String tableNumber, String name, String count, ButtonItemListener listener) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.name = name;
        this.count = count;
        this.listener = listener;
    }

    public int getId() {
        return id;
    }

    public boolean compareTo(WaiterItemModel other){
        return this.hashCode() == other.hashCode();
    }
}
