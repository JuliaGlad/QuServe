package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class WorkerManagerItemCallBack extends DiffUtil.ItemCallback<WorkerManagerModel> {

    @Override
    public boolean areItemsTheSame(@NonNull WorkerManagerModel oldItem, @NonNull WorkerManagerModel newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull WorkerManagerModel oldItem, @NonNull WorkerManagerModel newItem) {
        return oldItem.compareTo(newItem);
    }

}
