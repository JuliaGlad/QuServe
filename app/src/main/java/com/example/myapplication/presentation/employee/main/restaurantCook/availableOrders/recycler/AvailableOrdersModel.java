package com.example.myapplication.presentation.employee.main.restaurantCook.availableOrders.recycler;

import myapplication.android.ui.listeners.ButtonItemListener;

public class AvailableOrdersModel {
    int id;
    String tableNumber;
    String dishes;
    ButtonItemListener acceptListener;
    ButtonItemListener viewDetailsListener;

    public AvailableOrdersModel(int id, String tableNumber, String dishes, ButtonItemListener acceptListener, ButtonItemListener viewDetailsListener) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.dishes = dishes;
        this.acceptListener = acceptListener;
        this.viewDetailsListener = viewDetailsListener;
    }

    public boolean compareTo(AvailableOrdersModel other){
        return this.hashCode() == other.hashCode();
    }
}
