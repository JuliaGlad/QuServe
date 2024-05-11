package com.example.myapplication.presentation.companyQueue.queueManager.recycler_view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewQueueManagerItemBinding;

public class ManagerItemAdapter extends ListAdapter<ManagerItemModel, RecyclerView.ViewHolder> {

    public ManagerItemAdapter() {
        super(new ManagerItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewQueueManagerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewQueueManagerItemBinding binding;

        public ViewHolder(RecyclerViewQueueManagerItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(ManagerItemModel model){

            if (model.workers != null && Integer.parseInt(model.workers) > 3 ){
                model.workers = String.valueOf(Integer.parseInt(model.workers) - 3);
                binding.workersCount.setText("+" + model.workers);
            } else {
                binding.workersCount.setText(model.workers);
            }

            binding.queueName.setText(model.queueName);
            binding.location.setText(model.location);

            binding.queueItem.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }
    }
}
