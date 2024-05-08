package com.example.myapplication.presentation.home.recycler.homeDelegates.homeRestaurantLocationButton;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewHomeRestaurantLocationBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class HomeRestaurantLocationDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewHomeRestaurantLocationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((HomeRestaurantLocationModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof HomeRestaurantLocationDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewHomeRestaurantLocationBinding binding;

        public ViewHolder(RecyclerViewHomeRestaurantLocationBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(HomeRestaurantLocationModel model){
            binding.locationName.setText(model.location);
            binding.ordersCount.setText(model.ordersCount);
            binding.item.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }
    }
}
