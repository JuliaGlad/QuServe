package com.example.myapplication.presentation.queue.queueDetails.queueDetailsButton;

import android.graphics.drawable.Drawable;

public class QueueDetailButtonModel {
    int id;
    int title;
    int description;
    int drawable;
     QueueDetailButtonItemListener listener;

    public QueueDetailButtonModel(int id, int title, int description, int drawable, QueueDetailButtonItemListener listener){
        this.id = id;
        this.title = title;
        this.drawable = drawable;
        this.description = description;
        this.listener = listener;
    }
}
