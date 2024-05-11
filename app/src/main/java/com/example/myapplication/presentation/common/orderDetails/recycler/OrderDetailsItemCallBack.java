package com.example.myapplication.presentation.common.orderDetails.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class OrderDetailsItemCallBack extends DiffUtil.ItemCallback<OrderDetailsItemModel> {
    @Override
    public boolean areItemsTheSame(@NonNull OrderDetailsItemModel oldItem, @NonNull OrderDetailsItemModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull OrderDetailsItemModel oldItem, @NonNull OrderDetailsItemModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
