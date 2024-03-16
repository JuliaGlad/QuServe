package com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.delegateRecycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class RecyclerDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((RecyclerModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof RecyclerDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewBinding binding;

        public ViewHolder( RecyclerViewBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(RecyclerModel model){
            binding.recyclerView.setAdapter(model.adapter);
            model.adapter.submitList(model.list);
        }
    }
}
