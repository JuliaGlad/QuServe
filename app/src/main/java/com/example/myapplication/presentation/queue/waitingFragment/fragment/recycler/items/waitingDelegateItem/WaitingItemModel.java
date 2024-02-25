package com.example.myapplication.presentation.queue.waitingFragment.fragment.recycler.items.waitingDelegateItem;

import java.util.List;

public class WaitingItemModel {
    int id;
    String queueID;
    int size;
    String headerText;
    String descriptionText;
    boolean editable;
    String flag;

    public WaitingItemModel(int id, String queueID, int size, String headerText, String descriptionText, boolean editable, String flag) {
        this.id = id;
        this.queueID = queueID;
        this.size = size;
        this.headerText = headerText;
        this.descriptionText = descriptionText;
        this.editable = editable;
        this.flag = flag;
    }
}
