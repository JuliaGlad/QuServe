package com.example.myapplication.presentation.service.main.recyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class QueueButtonCallBack extends DiffUtil.ItemCallback<QueueButtonModel> {
    @Override
    public boolean areItemsTheSame(@NonNull QueueButtonModel oldItem, @NonNull QueueButtonModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull QueueButtonModel oldItem, @NonNull QueueButtonModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
