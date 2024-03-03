package com.example.myapplication.presentation.profile.loggedProfile.chooseCompany.companyDelegate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewCompanyItemBinding;

import myapplication.android.ui.listeners.ButtonItemListener;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class CompanyDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewCompanyItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((CompanyModel)item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof CompanyDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewCompanyItemBinding binding;

        public ViewHolder(RecyclerViewCompanyItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(CompanyModel model){
            binding.name.setText(model.name);
            binding.item.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }
    }
}
