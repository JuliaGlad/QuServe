package com.example.myapplication.presentation.home.homeDelegates.button;

import myapplication.android.ui.listeners.ButtonItemListener;

public class HomeButtonModel {
    int id;
    int title;
    int description;
    int drawable;
    ButtonItemListener listener;

    public HomeButtonModel(int id, int title, int description, int drawable, ButtonItemListener listener) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.drawable = drawable;
        this.listener = listener;
    }
}
