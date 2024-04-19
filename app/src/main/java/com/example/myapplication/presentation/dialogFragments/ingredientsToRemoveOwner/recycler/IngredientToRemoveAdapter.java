package com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOwner.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewIngredientToRemoveEditBinding;

public class IngredientToRemoveAdapter extends ListAdapter<IngredientToRemoveItemModel, RecyclerView.ViewHolder> {

    public IngredientToRemoveAdapter() {
        super(new IngredientToRemoveItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewIngredientToRemoveEditBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind((IngredientToRemoveItemModel) getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewIngredientToRemoveEditBinding binding;

        public ViewHolder(RecyclerViewIngredientToRemoveEditBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }
        void bind(IngredientToRemoveItemModel model){
            if (model.name != null) {
                binding.editTextIngredientName.setText(model.name);
            }
        }
    }

}
