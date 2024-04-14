package com.example.myapplication.presentation.service.main.restaurantService.recycler;

import myapplication.android.ui.listeners.ButtonItemListener;

public class ServiceRestaurantButtonModel {
    int id;
    int drawable;
    String title;
    ButtonItemListener listener;

    public ServiceRestaurantButtonModel(int id, int drawable, String title, ButtonItemListener listener) {
        this.id = id;
        this.drawable = drawable;
        this.title = title;
        this.listener = listener;
    }

    public boolean compareTo(ServiceRestaurantButtonModel other){
        return other.hashCode() == this.hashCode();
    }
}
