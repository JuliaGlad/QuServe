package com.example.myapplication.presentation.employee.main.queueWorkerFragment.delegates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewWorkerActiveQueueListItemBinding;

public class WorkerActiveQueueAdapter extends ListAdapter<WorkerActiveQueueModel, RecyclerView.ViewHolder> {

    public WorkerActiveQueueAdapter() {
        super(new WorkerActiveQueueCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewWorkerActiveQueueListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind((WorkerActiveQueueModel) getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewWorkerActiveQueueListItemBinding binding;

        public ViewHolder(RecyclerViewWorkerActiveQueueListItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(WorkerActiveQueueModel model){
            binding.queueName.setText(model.getName());
            binding.location.setText(model.getLocation());

            binding.item.setOnClickListener(v -> {
                model.getListener().onClick();
            });
        }
    }
}
