package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewTableItemLayoutBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class TableListDelegate implements AdapterDelegate {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewTableItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((TableListModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof TableListDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewTableItemLayoutBinding binding;

        public ViewHolder(RecyclerViewTableItemLayoutBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(TableListModel model){
            binding.tableNumber.setText(model.getNumber());
            binding.item.setOnClickListener(v -> {
                model.getListener().onClick();
            });
        }
    }
}
