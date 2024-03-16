package com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceRedItem;

import myapplication.android.ui.listeners.ButtonItemListener;

public class ServiceRedItemModel {
    int id;
    int drawable;
    int text;
    ButtonItemListener listener;

    public ServiceRedItemModel(int id, int drawable, int text, ButtonItemListener listener) {
        this.id = id;
        this.drawable = drawable;
        this.text = text;
        this.listener = listener;
    }
}
