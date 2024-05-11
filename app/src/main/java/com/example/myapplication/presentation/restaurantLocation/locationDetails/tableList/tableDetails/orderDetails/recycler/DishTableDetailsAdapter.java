package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.orderDetails.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewDishTableDetailsItemBinding;
import com.google.protobuf.Internal;

import java.util.List;

public class DishTableDetailsAdapter extends ListAdapter<DishTableDetailsItemModel, RecyclerView.ViewHolder> {

    public DishTableDetailsAdapter() {
        super(new DishTableDetailsItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewDishTableDetailsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewDishTableDetailsItemBinding binding;

        public ViewHolder(RecyclerViewDishTableDetailsItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(DishTableDetailsItemModel model){
            binding.name.setText(model.name);
            binding.number.setText(model.count);
        }
    }
}
