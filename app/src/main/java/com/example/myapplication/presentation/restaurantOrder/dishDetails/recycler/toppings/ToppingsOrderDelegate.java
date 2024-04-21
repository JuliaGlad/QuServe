package com.example.myapplication.presentation.restaurantOrder.dishDetails.recycler.toppings;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.databinding.RecyclerViewToppingsItemBinding;
import com.example.myapplication.presentation.restaurantMenu.model.VariantsModel;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ToppingsOrderDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {

    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return false;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewToppingsItemBinding binding;

        public ViewHolder(RecyclerViewToppingsItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(ToppingsOrderModel model) {
            binding.buttonDelete.setVisibility(View.GONE);
            binding.ingredientName.setText(model.name);
            binding.price.setText(model.price);

            model.image.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Glide.with(itemView.getContext())
                            .load(task.getResult())
                            .into(binding.ingredientImage);
                }
            });

            binding.item.setOnClickListener(v -> {
                if (!model.isChosen) {
                    model.isChosen = true;
                    binding.item.setStrokeColor(itemView.getResources().getColor(R.color.colorPrimary, itemView.getContext().getTheme()));
                    model.variants.add(new VariantsModel(model.name, model.price, model.image));
                } else {
                    model.isChosen = false;
                    binding.item.setStrokeColor(Color.TRANSPARENT);
                    for (int i = 0; i < model.variants.size(); i++) {
                        if (model.variants.get(i).getName().equals(model.name)){
                            model.variants.remove(i);
                            break;
                        }
                    }
                }
            });
        }
    }
}
