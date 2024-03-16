package com.example.myapplication.presentation.queue.participantList.statisticsDelegate;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class StatisticsDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {

    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return false;
    }
}
