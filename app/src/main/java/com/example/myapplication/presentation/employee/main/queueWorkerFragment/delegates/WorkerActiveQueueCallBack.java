package com.example.myapplication.presentation.employee.main.queueWorkerFragment.delegates;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class WorkerActiveQueueCallBack extends DiffUtil.ItemCallback<WorkerActiveQueueModel> {
    @Override
    public boolean areItemsTheSame(@NonNull WorkerActiveQueueModel oldItem, @NonNull WorkerActiveQueueModel newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull WorkerActiveQueueModel oldItem, @NonNull WorkerActiveQueueModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
