package com.example.myapplication.presentation.companyQueue.createQueue.delegates.workers;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class WorkerDelegateItem implements DelegateItem {

    WorkerModel value;

    public WorkerDelegateItem(WorkerModel value) {
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
