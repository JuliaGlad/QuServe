package com.example.myapplication.presentation.service.restaurantService.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class ServiceRestaurantButtonCallBack extends DiffUtil.ItemCallback<ServiceRestaurantButtonModel> {

    @Override
    public boolean areItemsTheSame(@NonNull ServiceRestaurantButtonModel oldItem, @NonNull ServiceRestaurantButtonModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull ServiceRestaurantButtonModel oldItem, @NonNull ServiceRestaurantButtonModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
