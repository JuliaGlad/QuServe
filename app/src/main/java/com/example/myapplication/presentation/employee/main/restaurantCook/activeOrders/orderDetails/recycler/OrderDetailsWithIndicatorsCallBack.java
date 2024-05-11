package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.orderDetails.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class OrderDetailsWithIndicatorsCallBack extends DiffUtil.ItemCallback<OrderDetailsWithIndicatorsItemModel> {
    @Override
    public boolean areItemsTheSame(@NonNull OrderDetailsWithIndicatorsItemModel oldItem, @NonNull OrderDetailsWithIndicatorsItemModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull OrderDetailsWithIndicatorsItemModel oldItem, @NonNull OrderDetailsWithIndicatorsItemModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
