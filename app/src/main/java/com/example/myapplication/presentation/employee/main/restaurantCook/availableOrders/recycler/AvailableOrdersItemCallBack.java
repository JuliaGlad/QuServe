package com.example.myapplication.presentation.employee.main.restaurantCook.availableOrders.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class AvailableOrdersItemCallBack extends DiffUtil.ItemCallback<AvailableOrdersModel> {
    @Override
    public boolean areItemsTheSame(@NonNull AvailableOrdersModel oldItem, @NonNull AvailableOrdersModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull AvailableOrdersModel oldItem, @NonNull AvailableOrdersModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
