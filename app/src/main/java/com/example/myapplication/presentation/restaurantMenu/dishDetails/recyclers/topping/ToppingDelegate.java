package com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.topping;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import myapplication.android.common_ui.databinding.RecyclerViewToppingsItemBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ToppingDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewToppingsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((ToppingDishDetailsModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof ToppingDishDetailsDelegateItem;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewToppingsItemBinding binding;

        public ViewHolder(RecyclerViewToppingsItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(ToppingDishDetailsModel model){
            binding.ingredientName.setText(model.name);
            String price = "+" + model.price;
            binding.price.setText(price);

            if (model.image != null){
                model.image.addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Glide.with(itemView.getContext())
                                .load(task.getResult())
                                .into(binding.ingredientImage);
                    }
                });
            }

            if (model.uri != null){
                Glide.with(itemView.getContext())
                        .load(model.uri)
                        .into(binding.ingredientImage);
            }

            binding.buttonDelete.setOnClickListener(v -> {
                model.listener.onClick();
            });

        }

    }
}
