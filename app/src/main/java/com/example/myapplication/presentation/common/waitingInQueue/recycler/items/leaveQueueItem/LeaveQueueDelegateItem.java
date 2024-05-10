package com.example.myapplication.presentation.common.waitingInQueue.recycler.items.leaveQueueItem;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class LeaveQueueDelegateItem implements DelegateItem {

    LeaveQueueModel value;

    public LeaveQueueDelegateItem(LeaveQueueModel value) {
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
