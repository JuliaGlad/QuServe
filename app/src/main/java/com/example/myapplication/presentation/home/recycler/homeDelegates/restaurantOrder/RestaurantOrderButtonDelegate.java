package com.example.myapplication.presentation.home.recycler.homeDelegates.restaurantOrder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.databinding.RecyclerViewRestaurantOrderButtonBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class RestaurantOrderButtonDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewRestaurantOrderButtonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((RestaurantOrderButtonModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof RestaurantOrderDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewRestaurantOrderButtonBinding binding;

        public ViewHolder(RecyclerViewRestaurantOrderButtonBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(RestaurantOrderButtonModel model){
            binding.item.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }


    }
}
