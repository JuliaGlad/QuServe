package com.example.myapplication.presentation.home.recycler.stories;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class StoryItemCallBack extends DiffUtil.ItemCallback<StoryModel> {
    @Override
    public boolean areItemsTheSame(@NonNull StoryModel oldItem, @NonNull StoryModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull StoryModel oldItem, @NonNull StoryModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
