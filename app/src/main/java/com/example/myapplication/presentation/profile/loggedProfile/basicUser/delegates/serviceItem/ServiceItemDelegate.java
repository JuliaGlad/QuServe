package com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.serviceItem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.RecyclerViewProfileButtonBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ServiceItemDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewProfileButtonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((ServiceItemModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof ServiceItemDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewProfileButtonBinding binding;

        public ViewHolder(RecyclerViewProfileButtonBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(ServiceItemModel model){
            binding.icon.setImageResource(model.drawable);
            binding.text.setText(model.text);
            binding.serviceButtonLayout.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }
    }
}
