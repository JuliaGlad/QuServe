package com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem;

import android.graphics.drawable.Drawable;

import myapplication.android.ui.listeners.ButtonItemListener;

public class ServiceItemModel {
    int id;
    int drawable;
    int text;
    ButtonItemListener listener;

    public ServiceItemModel(int id, int drawable, int text, ButtonItemListener listener) {
        this.id = id;
        this.drawable = drawable;
        this.text = text;
        this.listener = listener;
    }
}
