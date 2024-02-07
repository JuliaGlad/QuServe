package com.example.myapplication.presentation.queue.waitingFragment.fragment.recycler.items.waitingDelegateItem;

import java.util.List;

public class WaitingItemModel {
    int id;
    String queueID;
    List<String> list;
    String headerText;
    String descriptionText;
    boolean editable;
    String flag;

    public WaitingItemModel(int id, String queueID,List<String> list, String headerText, String descriptionText, boolean editable, String flag) {
        this.id = id;
        this.queueID = queueID;
        this.list = list;
        this.headerText = headerText;
        this.descriptionText = descriptionText;
        this.editable = editable;
        this.flag = flag;
    }
}
