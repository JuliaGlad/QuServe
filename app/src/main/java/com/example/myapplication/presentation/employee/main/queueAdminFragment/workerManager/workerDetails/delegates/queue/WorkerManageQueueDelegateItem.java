package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.workerDetails.delegates.queue;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class WorkerManageQueueDelegateItem implements DelegateItem<WorkerManagerQueueModel> {

    WorkerManagerQueueModel value;

    public WorkerManageQueueDelegateItem(WorkerManagerQueueModel value) {
        this.value = value;
    }

    @Override
    public WorkerManagerQueueModel content() {
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
