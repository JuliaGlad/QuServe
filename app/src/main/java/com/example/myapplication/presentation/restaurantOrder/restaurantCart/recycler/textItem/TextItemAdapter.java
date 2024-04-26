package com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.textItem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewTextItemBinding;

public class TextItemAdapter extends ListAdapter<TextItemModel, RecyclerView.ViewHolder> {

    public TextItemAdapter() {
        super(new TextItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewTextItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }
    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewTextItemBinding binding;

        public ViewHolder(RecyclerViewTextItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(TextItemModel model){
            binding.ingredientName.setText(model.name);
            binding.ingredientName.setOnClickListener(v -> model.listener.onClick());
        }
    }
}
