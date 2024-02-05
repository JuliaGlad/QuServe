package com.example.myapplication.presentation.queue.queueDetails.queueDetailsButton;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewQueueDetailsItemBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class QueueDetailButtonDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewQueueDetailsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((QueueDetailButtonDelegate.ViewHolder)holder).bind((QueueDetailButtonModel) item.content(), ((QueueDetailButtonModel) item.content()).listener);
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof QueueDetailsButtonDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

            RecyclerViewQueueDetailsItemBinding binding;

        public ViewHolder(RecyclerViewQueueDetailsItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        public void bind(QueueDetailButtonModel model, QueueDetailButtonItemListener listener){
            binding.title.setText(model.title);
            binding.description.setText(model.description);
            binding.queueDetailsItem.setOnClickListener(v -> listener.onClick());
        }
    }
}
