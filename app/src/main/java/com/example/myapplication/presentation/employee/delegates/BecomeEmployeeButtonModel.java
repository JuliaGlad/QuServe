package com.example.myapplication.presentation.employee.delegates;

import myapplication.android.ui.listeners.ButtonItemListener;

public class BecomeEmployeeButtonModel {
    int id;
    ButtonItemListener listener;

    public BecomeEmployeeButtonModel(int id, ButtonItemListener listener) {
        this.id = id;
        this.listener = listener;
    }
}
