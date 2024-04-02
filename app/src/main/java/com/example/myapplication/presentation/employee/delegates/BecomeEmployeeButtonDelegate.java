package com.example.myapplication.presentation.employee.delegates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewBecomeEmployeeButtonBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class BecomeEmployeeButtonDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewBecomeEmployeeButtonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((BecomeEmployeeButtonModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof BecomeEmployeeButtonModel;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewBecomeEmployeeButtonBinding binding;

        public ViewHolder(RecyclerViewBecomeEmployeeButtonBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(BecomeEmployeeButtonModel model){
            binding.item.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }
    }
}
