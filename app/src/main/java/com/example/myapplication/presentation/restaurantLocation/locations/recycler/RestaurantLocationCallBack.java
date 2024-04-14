package com.example.myapplication.presentation.restaurantLocation.locations.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class RestaurantLocationCallBack extends DiffUtil.ItemCallback<RestaurantLocationModel> {
    @Override
    public boolean areItemsTheSame(@NonNull RestaurantLocationModel oldItem, @NonNull RestaurantLocationModel newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull RestaurantLocationModel oldItem, @NonNull RestaurantLocationModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
