package com.example.myapplication.presentation.home.recycler.homeDelegates.restaurantOrder;

import androidx.activity.result.ActivityResultLauncher;

import com.journeyapps.barcodescanner.ScanOptions;

import myapplication.android.ui.listeners.ButtonItemListener;

public class RestaurantOrderButtonModel {
    int id;
    ActivityResultLauncher<ScanOptions> launcher;

    public RestaurantOrderButtonModel(int id, ActivityResultLauncher<ScanOptions> launcher) {
        this.id = id;
        this.launcher = launcher;
    }
}
