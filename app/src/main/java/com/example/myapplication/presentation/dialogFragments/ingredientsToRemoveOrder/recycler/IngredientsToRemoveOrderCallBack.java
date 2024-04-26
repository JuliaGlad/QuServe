package com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOrder.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class IngredientsToRemoveOrderCallBack extends DiffUtil.ItemCallback<IngredientsToRemoveOrderModel> {
    @Override
    public boolean areItemsTheSame(@NonNull IngredientsToRemoveOrderModel oldItem, @NonNull IngredientsToRemoveOrderModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull IngredientsToRemoveOrderModel oldItem, @NonNull IngredientsToRemoveOrderModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
