package com.example.myapplication.presentation.restaurantOrder.menu.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.RecyclerViewDishOrderMenuItemBinding;

public class DishOrderItemAdapter extends ListAdapter<DishOrderModel, RecyclerView.ViewHolder> {

    public DishOrderItemAdapter() {
        super(new DishOrderItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewDishOrderMenuItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind((DishOrderModel) getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewDishOrderMenuItemBinding binding;

        public ViewHolder(RecyclerViewDishOrderMenuItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(DishOrderModel model){
            binding.dishName.setText(model.getName());
            binding.price.setText(model.getPrice());

            if (model.getTask() != null){
                model.getTask().addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Glide.with(itemView.getContext())
                                .load(task.getResult())
                                .into(binding.dishImage);
                    }
                });
            }

            binding.item.setOnClickListener(v -> {
                model.getListener().onClick();
            });

            binding.buttonAddToCart.setOnClickListener(v -> {
                model.getListener().onClick();
            });
        }
    }
}
