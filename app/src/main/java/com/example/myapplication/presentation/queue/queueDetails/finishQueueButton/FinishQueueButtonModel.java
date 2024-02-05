package com.example.myapplication.presentation.queue.queueDetails.finishQueueButton;

public class FinishQueueButtonModel {
    int id;
    int text;
    FinishQueueButtonItemListener clickListener;

    public FinishQueueButtonModel(int id, int text, FinishQueueButtonItemListener clickListener) {
        this.id = id;
        this.text = text;
        this.clickListener = clickListener;
    }
}
