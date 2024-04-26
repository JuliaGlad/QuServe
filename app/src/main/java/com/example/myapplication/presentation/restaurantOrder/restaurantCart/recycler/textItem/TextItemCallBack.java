package com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.textItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class TextItemCallBack extends DiffUtil.ItemCallback<TextItemModel> {
    @Override
    public boolean areItemsTheSame(@NonNull TextItemModel oldItem, @NonNull TextItemModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull TextItemModel oldItem, @NonNull TextItemModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
