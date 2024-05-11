package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.orderDetails.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class DishTableDetailsItemCallBack extends DiffUtil.ItemCallback<DishTableDetailsItemModel> {
    @Override
    public boolean areItemsTheSame(@NonNull DishTableDetailsItemModel oldItem, @NonNull DishTableDetailsItemModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull DishTableDetailsItemModel oldItem, @NonNull DishTableDetailsItemModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
