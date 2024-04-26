package com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.requireChoiceCart;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class RequireChoiceCartCallBack extends DiffUtil.ItemCallback<RequireChoiceCartModel> {
    @Override
    public boolean areItemsTheSame(@NonNull RequireChoiceCartModel oldItem, @NonNull RequireChoiceCartModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull RequireChoiceCartModel oldItem, @NonNull RequireChoiceCartModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
