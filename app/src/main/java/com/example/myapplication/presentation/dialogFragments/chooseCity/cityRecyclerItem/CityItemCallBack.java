package com.example.myapplication.presentation.dialogFragments.chooseCity.cityRecyclerItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class CityItemCallBack extends DiffUtil.ItemCallback<CityItemModel> {
    @Override
    public boolean areItemsTheSame(@NonNull CityItemModel oldItem, @NonNull CityItemModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull CityItemModel oldItem, @NonNull CityItemModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
