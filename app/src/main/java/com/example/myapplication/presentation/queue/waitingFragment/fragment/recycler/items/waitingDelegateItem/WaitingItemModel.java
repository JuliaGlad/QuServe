package com.example.myapplication.presentation.queue.waitingFragment.fragment.recycler.items.waitingDelegateItem;

public class WaitingItemModel {
    int id;
    String queueID;
    int size;
    int drawable;
    String headerText;
    String descriptionText;
    boolean editable;
    String flag;

    public WaitingItemModel(int id, String queueID, int size, String headerText, String descriptionText, int drawable, boolean editable, String flag) {
        this.id = id;
        this.queueID = queueID;
        this.size = size;
        this.headerText = headerText;
        this.descriptionText = descriptionText;
        this.editable = editable;
        this.drawable = drawable;
        this.flag = flag;
    }
}
