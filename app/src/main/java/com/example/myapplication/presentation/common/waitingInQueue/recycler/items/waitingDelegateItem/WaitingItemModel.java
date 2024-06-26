package com.example.myapplication.presentation.common.waitingInQueue.recycler.items.waitingDelegateItem;

public class WaitingItemModel {
    int id;
    String path;
    int size;
    int drawable;
    String headerText;
    String descriptionText;
    int startValue;
    boolean editable;
    String flag;

    public WaitingItemModel(int id, int startValue, String path, int size, String headerText, String descriptionText, int drawable, boolean editable, String flag) {
        this.id = id;
        this.path = path;
        this.startValue = startValue;
        this.size = size;
        this.headerText = headerText;
        this.descriptionText = descriptionText;
        this.editable = editable;
        this.drawable = drawable;
        this.flag = flag;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }
}
