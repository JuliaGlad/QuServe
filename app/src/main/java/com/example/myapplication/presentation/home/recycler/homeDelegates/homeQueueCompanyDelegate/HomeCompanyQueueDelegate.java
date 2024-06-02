package com.example.myapplication.presentation.home.recycler.homeDelegates.homeQueueCompanyDelegate;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewHomeCompanyQueueDelegateBinding;
import com.example.myapplication.databinding.RecyclerViewHomeQueueActionButtonLayoutBinding;
import com.example.myapplication.presentation.home.recycler.homeDelegates.homeQueueActionButton.QueueActionButtonDelegate;
import com.example.myapplication.presentation.home.recycler.homeDelegates.homeQueueActionButton.QueueActionButtonModel;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class HomeCompanyQueueDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new HomeCompanyQueueDelegate.ViewHolder(RecyclerViewHomeCompanyQueueDelegateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((HomeCompanyQueueDelegate.ViewHolder)holder).bind((HomeCompanyQueueModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof HomeCompanyQueueDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewHomeCompanyQueueDelegateBinding binding;

        public ViewHolder(RecyclerViewHomeCompanyQueueDelegateBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(HomeCompanyQueueModel model){
            binding.queueName.setText(model.name);
            binding.peopleInQueue.setText(model.peopleInQueue);
            binding.item.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }
    }
}
