package com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.requireChoiceCart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewRequireChoiceTextBinding;

public class RequireChoiceAdapter extends ListAdapter<RequireChoiceCartModel, RecyclerView.ViewHolder> {

    public RequireChoiceAdapter() {
        super(new RequireChoiceCartCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewRequireChoiceTextBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewRequireChoiceTextBinding binding;

        public ViewHolder(RecyclerViewRequireChoiceTextBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(RequireChoiceCartModel model){
            binding.variantName.setText(model.name);
        }
    }
}
