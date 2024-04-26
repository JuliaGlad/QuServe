package com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.cartDishItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class CartDishItemCallBack extends DiffUtil.ItemCallback<CartDishItemModel> {

    @Override
    public boolean areItemsTheSame(@NonNull CartDishItemModel oldItem, @NonNull CartDishItemModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull CartDishItemModel oldItem, @NonNull CartDishItemModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
