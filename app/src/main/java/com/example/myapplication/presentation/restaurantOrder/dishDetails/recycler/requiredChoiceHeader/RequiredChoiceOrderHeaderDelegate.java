package com.example.myapplication.presentation.restaurantOrder.dishDetails.recycler.requiredChoiceHeader;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewRequiredChoiceOrderHeaderBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class RequiredChoiceOrderHeaderDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewRequiredChoiceOrderHeaderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((RequiredChoiceOrderHeaderModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof RequiredChoiceOrderHeaderDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewRequiredChoiceOrderHeaderBinding binding;

        public ViewHolder(RecyclerViewRequiredChoiceOrderHeaderBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(RequiredChoiceOrderHeaderModel model){
            binding.choiceName.setText(model.name);
        }
    }
}
