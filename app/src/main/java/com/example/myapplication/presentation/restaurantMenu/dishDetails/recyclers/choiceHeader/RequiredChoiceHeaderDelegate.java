package com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.choiceHeader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewRequiredChoiceHeaderBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class RequiredChoiceHeaderDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewRequiredChoiceHeaderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((RequiredChoiceHeaderModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof RequiredChoiceHeaderDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewRequiredChoiceHeaderBinding binding;

        public ViewHolder(RecyclerViewRequiredChoiceHeaderBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(RequiredChoiceHeaderModel model){
            binding.choiceName.setText(model.name);
            binding.buttonEdit.setOnClickListener(v -> model.listener.onClick());
        }
    }
}
