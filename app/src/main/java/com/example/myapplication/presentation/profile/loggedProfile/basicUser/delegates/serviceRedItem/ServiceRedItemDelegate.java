package com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.serviceRedItem;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewProfileButtonBinding;
import com.example.myapplication.databinding.RecyclerViewProfileButtonRedBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ServiceRedItemDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder( RecyclerViewProfileButtonRedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((ServiceRedItemModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof ServiceRedItemDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewProfileButtonRedBinding binding;

        public ViewHolder( RecyclerViewProfileButtonRedBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(ServiceRedItemModel model){
            binding.icon.setImageResource(model.drawable);
            binding.text.setText(model.text);
            binding.serviceButtonLayout.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }
    }
}
