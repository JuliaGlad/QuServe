package com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.addTopping;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewAddToppingItemBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class AddToppingDelegate implements AdapterDelegate {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewAddToppingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((AddToppingModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof AddToppingDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewAddToppingItemBinding binding;

        public ViewHolder(RecyclerViewAddToppingItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(AddToppingModel model){
            binding.item.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }
    }
}
