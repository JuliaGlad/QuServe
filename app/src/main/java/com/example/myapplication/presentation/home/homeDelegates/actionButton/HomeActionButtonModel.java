package com.example.myapplication.presentation.home.homeDelegates.actionButton;

import myapplication.android.ui.listeners.ButtonItemListener;

public class HomeActionButtonModel {
    int id;
    String title;
    int role;
    String type;
    ButtonItemListener listener;

    public HomeActionButtonModel(int id, String title, int role, String type, ButtonItemListener listener) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.listener = listener;
        this.role = role;
    }
}