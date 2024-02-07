package com.example.myapplication.presentation.queue.waitingFragment.fragment.recycler.items.leaveQueueItem;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.databinding.RecyclerViewLeaveQueueItemBinding;

import myapplication.android.ui.listeners.ButtonItemListener;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class LeaveQueueDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewLeaveQueueItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind( ((LeaveQueueModel) item.content()).listener);
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof LeaveQueueDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewLeaveQueueItemBinding binding;

        public ViewHolder(RecyclerViewLeaveQueueItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind( ButtonItemListener listener){
            binding.leaveQueueButton.setOnClickListener(v -> listener.onClick());
        }

    }

}
