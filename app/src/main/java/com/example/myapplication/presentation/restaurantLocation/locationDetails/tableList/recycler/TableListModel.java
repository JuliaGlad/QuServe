package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.recycler;

import androidx.annotation.Nullable;

import io.reactivex.rxjava3.annotations.NonNull;
import myapplication.android.ui.listeners.ButtonItemListener;

public class TableListModel {
    private final int id;
    private final String number;
    private final ButtonItemListener listener;

    public TableListModel(int id, String number, ButtonItemListener listener) {
        this.id = id;
        this.number = number;
        this.listener = listener;
    }

    public boolean compareTo(TableListModel other){
       return this.hashCode() == other.hashCode();
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public ButtonItemListener getListener() {
        return listener;
    }
}
