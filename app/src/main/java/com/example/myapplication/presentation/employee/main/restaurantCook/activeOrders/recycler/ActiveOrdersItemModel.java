package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.recycler;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

import myapplication.android.ui.listeners.ButtonItemListener;

public class ActiveOrdersItemModel {
    int id;
    String tableNumber;
    String dishesCount;
    ButtonItemListener listener;

    public ActiveOrdersItemModel(int id, String tableNumber, String dishesCount, ButtonItemListener listener) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.dishesCount = dishesCount;
        this.listener = listener;
    }

    public boolean compareTo(ActiveOrdersItemModel other){
        return this.hashCode() == other.hashCode();
    }
}
