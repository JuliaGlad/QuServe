package com.example.myapplication.presentation.restaurantMenu.addDish.recycler;

import android.os.Bundle;

import myapplication.android.ui.listeners.ButtonObjectListener;
import myapplication.android.ui.listeners.ButtonStringListener;

public class NumberPickerModel {
    int id;
    Bundle bundle;
    ButtonObjectListener hoursListener;

    public NumberPickerModel(int id, Bundle bundle, ButtonObjectListener hoursListener) {
        this.id = id;
        this.bundle = bundle;
        this.hoursListener = hoursListener;
    }
}
