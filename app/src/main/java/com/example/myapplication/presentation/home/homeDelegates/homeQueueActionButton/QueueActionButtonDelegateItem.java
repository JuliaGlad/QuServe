package com.example.myapplication.presentation.home.homeDelegates.homeQueueActionButton;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class QueueActionButtonDelegateItem implements DelegateItem<QueueActionButtonModel> {

    QueueActionButtonModel value;

    public QueueActionButtonDelegateItem(QueueActionButtonModel value) {
        this.value = value;
    }

    @Override
    public QueueActionButtonModel content() {
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
