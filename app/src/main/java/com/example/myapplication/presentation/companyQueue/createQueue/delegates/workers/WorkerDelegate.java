package com.example.myapplication.presentation.companyQueue.createQueue.delegates.workers;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewWorkerItemBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class WorkerDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewWorkerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((WorkerModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof WorkerDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewWorkerItemBinding binding;

        public ViewHolder(RecyclerViewWorkerItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(WorkerModel model){
            binding.name.setText(model.name);
        }
    }
}
