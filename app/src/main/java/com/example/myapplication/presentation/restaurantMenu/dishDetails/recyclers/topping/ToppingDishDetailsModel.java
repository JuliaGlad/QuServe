package com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.topping;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

import myapplication.android.ui.listeners.ButtonItemListener;

public class ToppingDishDetailsModel {
    int id;
    String name;
    String price;
    Task<Uri> image;
    Uri uri;
    ButtonItemListener listener;

    public ToppingDishDetailsModel(int id, String name, String price, Task<Uri> image, Uri uri, ButtonItemListener listener) {
        this.id = id;
        this.name = name;
        this.uri = uri;
        this.price = price;
        this.image = image;
        this.listener = listener;
    }
}
