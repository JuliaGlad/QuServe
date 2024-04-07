package com.example.myapplication.presentation.service.main.recyclerView;

import myapplication.android.ui.listeners.ButtonItemListener;

public class QueueButtonModel {
    int id;
    int drawable;
    String title;
    ButtonItemListener listener;

    public QueueButtonModel(int id, int drawable, String title, ButtonItemListener listener) {
        this.id = id;
        this.drawable = drawable;
        this.title = title;
        this.listener = listener;
    }

    public boolean compareTo(QueueButtonModel other){
        return other.hashCode() == this.hashCode();
    }
}
