package com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class WorkerItemCallBack extends DiffUtil.ItemCallback<WorkerItemModel> {
    @Override
    public boolean areItemsTheSame(@NonNull WorkerItemModel oldItem, @NonNull WorkerItemModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull WorkerItemModel oldItem, @NonNull WorkerItemModel newItem) {
        return oldItem.compareToOther(newItem);
    }
}
