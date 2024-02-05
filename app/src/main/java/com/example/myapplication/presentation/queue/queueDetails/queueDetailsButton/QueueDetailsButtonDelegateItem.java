package com.example.myapplication.presentation.queue.queueDetails.queueDetailsButton;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class QueueDetailsButtonDelegateItem implements DelegateItem {

    private QueueDetailButtonModel value;

    public QueueDetailsButtonDelegateItem(QueueDetailButtonModel value){
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
