package com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOwner.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class IngredientToRemoveItemCallBack extends DiffUtil.ItemCallback<IngredientToRemoveItemModel> {
    @Override
    public boolean areItemsTheSame(@NonNull IngredientToRemoveItemModel oldItem, @NonNull IngredientToRemoveItemModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull IngredientToRemoveItemModel oldItem, @NonNull IngredientToRemoveItemModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
