package com.example.myapplication.presentation.companyQueue.queueDetails.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class QueueEmployeeCallBack extends DiffUtil.ItemCallback<QueueEmployeeModel> {
    @Override
    public boolean areItemsTheSame(@NonNull QueueEmployeeModel oldItem, @NonNull QueueEmployeeModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull QueueEmployeeModel oldItem, @NonNull QueueEmployeeModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
