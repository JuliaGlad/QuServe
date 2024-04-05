package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.workerDetails.delegates.queue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewQueueWorkerManagerBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class WorkerManagerQueueDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewQueueWorkerManagerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((WorkerManagerQueueModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof WorkerManageQueueDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewQueueWorkerManagerBinding binding;

        public ViewHolder( RecyclerViewQueueWorkerManagerBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(WorkerManagerQueueModel model){
            binding.headLine.setText(model.getName());
            binding.description.setText(model.getLocation());
        }
    }
}
