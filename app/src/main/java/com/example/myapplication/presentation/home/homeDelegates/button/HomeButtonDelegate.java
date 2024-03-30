package com.example.myapplication.presentation.home.homeDelegates.button;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ServiceQueueButtonLayoutBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class HomeButtonDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(ServiceQueueButtonLayoutBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((HomeButtonModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof HomeButtonDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ServiceQueueButtonLayoutBinding binding;

        public ViewHolder(ServiceQueueButtonLayoutBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(HomeButtonModel model){
            binding.headLine.setText(model.title);
            binding.description.setText(model.description);
            binding.icon.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(), model.drawable, itemView.getContext().getTheme()));
            binding.item.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }

    }
}
