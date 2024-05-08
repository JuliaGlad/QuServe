package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.RecyclerViewActiveOrderItemBinding;

public class ActiveOrdersAdapter extends ListAdapter<ActiveOrdersItemModel, RecyclerView.ViewHolder> {

    public ActiveOrdersAdapter() {
        super(new ActiveOrdersItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewActiveOrderItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewActiveOrderItemBinding binding;

        public ViewHolder(RecyclerViewActiveOrderItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(ActiveOrdersItemModel model){
            binding.number.setText(model.tableNumber);
            binding.count.setText(model.dishesCount);
            binding.viewDetails.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }
    }
}
