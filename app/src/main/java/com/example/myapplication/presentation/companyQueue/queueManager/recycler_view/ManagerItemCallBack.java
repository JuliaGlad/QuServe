package com.example.myapplication.presentation.companyQueue.queueManager.recycler_view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class ManagerItemCallBack extends DiffUtil.ItemCallback<ManagerItemModel> {


    @Override
    public boolean areItemsTheSame(@NonNull ManagerItemModel oldItem, @NonNull ManagerItemModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull ManagerItemModel oldItem, @NonNull ManagerItemModel newItem) {
        return oldItem.compareToOther(newItem);
    }
}
