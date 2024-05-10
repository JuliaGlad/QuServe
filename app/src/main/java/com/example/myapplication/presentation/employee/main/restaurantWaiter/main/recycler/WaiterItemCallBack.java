package com.example.myapplication.presentation.employee.main.restaurantWaiter.main.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class WaiterItemCallBack extends DiffUtil.ItemCallback<WaiterItemModel> {
    @Override
    public boolean areItemsTheSame(@NonNull WaiterItemModel oldItem, @NonNull WaiterItemModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull WaiterItemModel oldItem, @NonNull WaiterItemModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
