package com.example.myapplication.presentation.employee.main.restaurantCook.availableOrders.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewAvailableOrdersBinding;

public class AvailableOrdersAdapter extends ListAdapter<AvailableOrdersModel, RecyclerView.ViewHolder> {

    public AvailableOrdersAdapter() {
        super(new AvailableOrdersItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewAvailableOrdersBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewAvailableOrdersBinding binding;

        public ViewHolder(RecyclerViewAvailableOrdersBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(AvailableOrdersModel model){
            binding.count.setText(model.dishes);
            binding.number.setText(model.tableNumber);
            binding.buttonAccept.setOnClickListener(v -> {
                model.acceptListener.onClick();
            });
            binding.buttonSeeDetails.setOnClickListener(v -> {
                model.viewDetailsListener.onClick();
            });
        }
    }
}
