package com.example.myapplication.presentation.restaurantMenu.dishDetails.editRequiredChoice.recyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class EditRequiredChoiceItemCallBack extends DiffUtil.ItemCallback<EditRequiredChoiceItemModel> {
    @Override
    public boolean areItemsTheSame(@NonNull EditRequiredChoiceItemModel oldItem, @NonNull EditRequiredChoiceItemModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull EditRequiredChoiceItemModel oldItem, @NonNull EditRequiredChoiceItemModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
