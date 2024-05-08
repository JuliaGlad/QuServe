package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class ActiveOrdersItemCallBack extends DiffUtil.ItemCallback<ActiveOrdersItemModel> {
    @Override
    public boolean areItemsTheSame(@NonNull ActiveOrdersItemModel oldItem, @NonNull ActiveOrdersItemModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull ActiveOrdersItemModel oldItem, @NonNull ActiveOrdersItemModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
