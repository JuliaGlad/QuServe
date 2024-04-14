package com.example.myapplication.presentation.restaurantLocation.locations.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewRestaurantLocationBinding;

public class RestaurantLocationAdapter extends ListAdapter<RestaurantLocationModel, RecyclerView.ViewHolder> {

    public RestaurantLocationAdapter() {
        super(new RestaurantLocationCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewRestaurantLocationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewRestaurantLocationBinding binding;

        public ViewHolder(RecyclerViewRestaurantLocationBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(RestaurantLocationModel model){
            binding.location.setText(model.getLocation());
            binding.cooksCount.setText(model.getCookCount());
            binding.waiterCount.setText(model.getWaitersCount());

            binding.item.setOnClickListener(v -> {
                model.getListener().onClick();
            });
        }
    }
}
