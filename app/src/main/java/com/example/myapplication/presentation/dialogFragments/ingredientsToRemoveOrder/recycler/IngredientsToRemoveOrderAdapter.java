package com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOrder.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewIngredientToRemoveOrderBinding;

public class IngredientsToRemoveOrderAdapter extends ListAdapter<IngredientsToRemoveOrderModel, RecyclerView.ViewHolder> {

    public IngredientsToRemoveOrderAdapter() {
        super(new IngredientsToRemoveOrderCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewIngredientToRemoveOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewIngredientToRemoveOrderBinding binding;

        public ViewHolder(RecyclerViewIngredientToRemoveOrderBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(IngredientsToRemoveOrderModel model) {

            binding.textIngredientName.setText(model.name);

            binding.item.setOnClickListener(v -> {
                if (!model.isAdded) {
                    model.isAdded = true;
                    model.added.add(model.name);
                    binding.imageDone.setVisibility(View.VISIBLE);
                } else {
                    model.isAdded = false;
                    model.added.remove(model.name);
                    binding.imageDone.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}
