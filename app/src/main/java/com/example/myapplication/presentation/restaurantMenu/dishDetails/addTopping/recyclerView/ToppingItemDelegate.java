package com.example.myapplication.presentation.restaurantMenu.dishDetails.addTopping.recyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.RecyclerViewAddToppingCenterItemBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ToppingItemDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewAddToppingCenterItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((ToppingModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof ToppingDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewAddToppingCenterItemBinding binding;

        public ViewHolder( RecyclerViewAddToppingCenterItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(ToppingModel model){
            binding.ingredientName.setText(model.name);
            String price = "+" + model.price;
            binding.price.setText(price);

            if (model.uri != null){
                Glide.with(itemView.getContext())
                        .load(model.uri)
                        .into(binding.ingredientImage);
            }

            binding.item.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }
    }
}
