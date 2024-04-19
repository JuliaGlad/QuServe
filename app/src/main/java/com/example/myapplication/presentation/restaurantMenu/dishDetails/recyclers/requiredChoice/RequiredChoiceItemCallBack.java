package com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.requiredChoice;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class RequiredChoiceItemCallBack extends DiffUtil.ItemCallback<RequiredChoiceItemModel> {

    @Override
    public boolean areItemsTheSame(@NonNull RequiredChoiceItemModel oldItem, @NonNull RequiredChoiceItemModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull RequiredChoiceItemModel oldItem, @NonNull RequiredChoiceItemModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
