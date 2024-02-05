package com.example.myapplication.presentation.queue.queueDetails.queueDetailsButton;

public class QueueDetailButtonModel {
    int id;
    int title;
    int description;
     QueueDetailButtonItemListener listener;

    public QueueDetailButtonModel(int id, int title, int description, QueueDetailButtonItemListener listener){
        this.id = id;
        this.title = title;
        this.description = description;
        this.listener = listener;
    }
}
