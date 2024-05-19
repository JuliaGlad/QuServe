package com.example.myapplication.presentation.restaurantMenu.dishItem;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.RecyclerViewDishItemBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class DishItemDelegate implements AdapterDelegate {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewDishItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((DishItemModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof DishItemDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewDishItemBinding binding;

        public ViewHolder(RecyclerViewDishItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(DishItemModel model) {
            if (model.getTask() != null) {
                model.getTask().addOnCompleteListener(task -> {
                    Glide.with(itemView.getContext())
                            .load(task.getResult())
                            .into(binding.dishImage);
                });
            }

            if (model.getUri() != Uri.EMPTY){
                Log.i("Uri", String.valueOf(model.getUri()));
                Glide.with(itemView.getContext())
                        .load(model.getUri())
                        .into(binding.dishImage);
            }

            binding.dishName.setText(model.getName());
            binding.price.setText(model.getPrice().concat("₽"));
            binding.weightCount.setText(model.getWeight());

            binding.item.setOnClickListener(v -> {
                model.getListener().onClick();
            });
        }
    }
}
