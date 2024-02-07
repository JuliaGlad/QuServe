package com.example.myapplication.presentation.queue.waitingFragment.fragment.recycler.items.waitingDelegateItem;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewWaitingItemBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class WaitingItemDelegate implements AdapterDelegate {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewWaitingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((WaitingItemDelegate.ViewHolder)holder).bind((WaitingItemModel)item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof WaitingItemDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewWaitingItemBinding binding;

        public ViewHolder(RecyclerViewWaitingItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(WaitingItemModel model){
            binding.header.setText(model.headerText);
            binding.description.setText(model.descriptionText);
        }
    }

}
