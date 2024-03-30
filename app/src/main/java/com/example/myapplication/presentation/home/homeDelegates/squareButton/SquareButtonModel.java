package com.example.myapplication.presentation.home.homeDelegates.squareButton;

import myapplication.android.ui.listeners.ButtonItemListener;

public class SquareButtonModel {
    int id;
    int firstDrawable;
    int secondDrawable;
    int firstTitle;
    int secondTitle;
    ButtonItemListener firstListener;
    ButtonItemListener secondListener;

    public SquareButtonModel(int id, int firstTitle, int secondTitle, int firstDrawable, int secondDrawable, ButtonItemListener firstListener, ButtonItemListener secondListener) {
        this.id = id;
        this.firstTitle = firstTitle;
        this.secondTitle = secondTitle;
        this.firstDrawable = firstDrawable;
        this.secondDrawable = secondDrawable;
        this.firstListener = firstListener;
        this.secondListener = secondListener;
    }
}
