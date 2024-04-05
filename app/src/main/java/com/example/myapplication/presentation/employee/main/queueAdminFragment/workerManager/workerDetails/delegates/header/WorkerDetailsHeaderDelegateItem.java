package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.workerDetails.delegates.header;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class WorkerDetailsHeaderDelegateItem implements DelegateItem<WorkerDetailsHeaderModel> {

    WorkerDetailsHeaderModel value;

    public WorkerDetailsHeaderDelegateItem(WorkerDetailsHeaderModel value) {
        this.value = value;
    }

    @Override
    public WorkerDetailsHeaderModel content() {
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
