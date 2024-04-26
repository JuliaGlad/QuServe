package com.example.myapplication.presentation.restaurantOrder.restaurantCart;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class CartDishUriModel {
    private final Task<Uri> task;

    public CartDishUriModel(Task<Uri> task) {
        this.task = task;
    }
}
