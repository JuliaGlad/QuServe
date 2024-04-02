package com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class AddWorkerItemCallBack extends DiffUtil.ItemCallback<AddWorkerRecyclerModel> {
    @Override
    public boolean areItemsTheSame(@NonNull AddWorkerRecyclerModel oldItem, @NonNull AddWorkerRecyclerModel newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull AddWorkerRecyclerModel oldItem, @NonNull AddWorkerRecyclerModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
