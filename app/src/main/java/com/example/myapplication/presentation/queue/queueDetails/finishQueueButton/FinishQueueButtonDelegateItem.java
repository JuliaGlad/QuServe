package com.example.myapplication.presentation.queue.queueDetails.finishQueueButton;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class FinishQueueButtonDelegateItem implements DelegateItem {
    private FinishQueueButtonModel value;

    public FinishQueueButtonDelegateItem( FinishQueueButtonModel value) {
        this.value = value;
    }

    @Override
    public Object content() {
        return value;
    }

    @Override
    public int id() {
        return value.hashCode();
    }


    @Override
    public boolean compareToOther(DelegateItem other) {
        return other.content() == content();
    }
}
