package com.example.myapplication.presentation.home.recycler.homeDelegates.restaurantOrder;

import androidx.activity.result.ActivityResultLauncher;

import com.journeyapps.barcodescanner.ScanOptions;

import myapplication.android.ui.listeners.ButtonItemListener;

public class RestaurantOrderButtonModel {
    int id;
    ButtonItemListener listener;

    public RestaurantOrderButtonModel(int id, ButtonItemListener listener) {
        this.id = id;
        this.listener = listener;
    }
}
