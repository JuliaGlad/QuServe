package com.example.myapplication.presentation.home.homeDelegates.homeQueueActionButton;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewHomeQueueActionButtonLayoutBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class QueueActionButtonDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewHomeQueueActionButtonLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((QueueActionButtonModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof QueueActionButtonDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewHomeQueueActionButtonLayoutBinding binding;

        public ViewHolder(RecyclerViewHomeQueueActionButtonLayoutBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(QueueActionButtonModel model){
            binding.queueName.setText(model.name);
            binding.peopleInQueue.setText(model.peopleInQueue);
            binding.item.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }
    }
}
