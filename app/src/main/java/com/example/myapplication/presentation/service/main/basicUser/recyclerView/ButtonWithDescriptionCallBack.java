package com.example.myapplication.presentation.service.main.basicUser.recyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class ButtonWithDescriptionCallBack extends DiffUtil.ItemCallback<ButtonWithDescriptionModel> {
    @Override
    public boolean areItemsTheSame(@NonNull ButtonWithDescriptionModel oldItem, @NonNull ButtonWithDescriptionModel newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull ButtonWithDescriptionModel oldItem, @NonNull ButtonWithDescriptionModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
