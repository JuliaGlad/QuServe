package com.example.myapplication.presentation.companyQueue.queueDetails.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class QueueEmployeeCallBack extends DiffUtil.ItemCallback<RecyclerEmployeeModel> {
    @Override
    public boolean areItemsTheSame(@NonNull RecyclerEmployeeModel oldItem, @NonNull RecyclerEmployeeModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull RecyclerEmployeeModel oldItem, @NonNull RecyclerEmployeeModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
