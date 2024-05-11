package com.example.myapplication.presentation.employee.main.companiesEmployees.employeesRestaurant.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class RestaurantEmployeeItemCallBack extends DiffUtil.ItemCallback<RestaurantEmployeeItemModel> {
    @Override
    public boolean areItemsTheSame(@NonNull RestaurantEmployeeItemModel oldItem, @NonNull RestaurantEmployeeItemModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull RestaurantEmployeeItemModel oldItem, @NonNull RestaurantEmployeeItemModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
