package com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.userItemDelegate;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.databinding.RecyclerViewSettingsUserImageBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class SettingsUserItemDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewSettingsUserImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((SettingsUserItemModel)item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof SettingsUserItemDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewSettingsUserImageBinding binding;

        public ViewHolder(RecyclerViewSettingsUserImageBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(SettingsUserItemModel model){
            binding.userName.setText(model.name);
            binding.userEmail.setText(model.email);
            if (model.uri != null){
                Glide.with(itemView.getContext())
                        .load(model.uri)
                        .apply(RequestOptions.circleCropTransform())
                        .into(binding.userPhoto);
            }
        }
    }
}
