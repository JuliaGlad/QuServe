package com.example.myapplication.presentation.restaurantOrder.dishDetails.recycler.toppings;

import android.net.Uri;

import com.example.myapplication.presentation.restaurantMenu.model.VariantsModel;
import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;
import com.google.android.gms.tasks.Task;

import java.util.List;

import myapplication.android.ui.listeners.ButtonItemListener;
import myapplication.android.ui.listeners.ButtonStringListener;

public class ToppingsOrderModel {
    int id;
    String name;
    String price;
    Task<Uri> image;
    boolean isChosen = false;
    List<VariantCartModel> variants;
    ButtonItemListener listenerAdd;
    ButtonItemListener listenerRemove;

    public ToppingsOrderModel(int id, String name, String price, Task<Uri> image, List<VariantCartModel> variants, ButtonItemListener listenerAdd, ButtonItemListener listenerRemove) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.listenerAdd = listenerAdd;
        this.listenerRemove = listenerRemove;
        this.image = image;
        this.variants = variants;
    }

}
