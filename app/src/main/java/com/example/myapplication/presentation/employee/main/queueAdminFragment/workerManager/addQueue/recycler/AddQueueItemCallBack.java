package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.addQueue.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class AddQueueItemCallBack extends DiffUtil.ItemCallback<AddQueueItemModel> {


    @Override
    public boolean areItemsTheSame(@NonNull AddQueueItemModel oldItem, @NonNull AddQueueItemModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull AddQueueItemModel oldItem, @NonNull AddQueueItemModel newItem) {
        return oldItem.compareToOther(newItem);
    }
}
