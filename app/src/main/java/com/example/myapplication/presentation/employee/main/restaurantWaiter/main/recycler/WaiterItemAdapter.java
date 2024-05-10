package com.example.myapplication.presentation.employee.main.restaurantWaiter.main.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewWaiterItemBinding;

public class WaiterItemAdapter extends ListAdapter<WaiterItemModel, RecyclerView.ViewHolder> {

    public WaiterItemAdapter() {
        super(new WaiterItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewWaiterItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewWaiterItemBinding binding;

        public ViewHolder(RecyclerViewWaiterItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(WaiterItemModel model){
            binding.count.setText(model.count);
            binding.tableNumber.setText(model.tableNumber);
            binding.dishesTitle.setText(model.name);
            binding.served.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }
    }
}
