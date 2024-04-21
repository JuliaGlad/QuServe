package com.example.myapplication.presentation.restaurantOrder.menu.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class DishOrderItemCallBack extends DiffUtil.ItemCallback<DishOrderModel> {
    @Override
    public boolean areItemsTheSame(@NonNull DishOrderModel oldItem, @NonNull DishOrderModel newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull DishOrderModel oldItem, @NonNull DishOrderModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
