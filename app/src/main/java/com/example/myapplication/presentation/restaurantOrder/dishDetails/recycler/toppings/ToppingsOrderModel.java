package com.example.myapplication.presentation.restaurantOrder.dishDetails.recycler.toppings;

import android.net.Uri;

import com.example.myapplication.presentation.restaurantMenu.model.VariantsModel;
import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;
import com.google.android.gms.tasks.Task;

import java.util.List;

import myapplication.android.ui.listeners.ButtonItemListener;

public class ToppingsOrderModel {
    int id;
    String name;
    String price;
    Task<Uri> image;
    boolean isChosen = false;
    String totalPrice;
    List<VariantCartModel> variants;

    public ToppingsOrderModel(int id, String name, String price, Task<Uri> image, String totalPrice, List<VariantCartModel> variants) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.totalPrice = totalPrice;
        this.variants = variants;
    }

}
