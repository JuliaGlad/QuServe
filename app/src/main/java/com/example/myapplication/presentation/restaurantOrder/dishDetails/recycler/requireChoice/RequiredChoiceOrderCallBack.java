package com.example.myapplication.presentation.restaurantOrder.dishDetails.recycler.requireChoice;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class RequiredChoiceOrderCallBack extends DiffUtil.ItemCallback<RequireChoiceOrderModel> {

    @Override
    public boolean areItemsTheSame(@NonNull RequireChoiceOrderModel oldItem, @NonNull RequireChoiceOrderModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull RequireChoiceOrderModel oldItem, @NonNull RequireChoiceOrderModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
