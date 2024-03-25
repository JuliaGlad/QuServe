package com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.delegates.history;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewHistoryItemBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class HistoryDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewHistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((HistoryDelegateModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof HistoryDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewHistoryItemBinding binding;

        public ViewHolder(RecyclerViewHistoryItemBinding _binding) {
            super(_binding.getRoot());

            binding = _binding;
        }

        void bind(HistoryDelegateModel model){
            binding.queueName.setText(model.name);
            binding.time.setText(model.time);
        }
    }
}
