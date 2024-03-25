package com.example.myapplication.presentation.companyQueue.queueDetails.recycler;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewEmployeeItemBinding;

public class QueueEmployeeAdapter extends ListAdapter<QueueEmployeeModel, RecyclerView.ViewHolder> {

    public QueueEmployeeAdapter() {
        super(new QueueEmployeeCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewEmployeeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind((QueueEmployeeModel) getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewEmployeeItemBinding binding;

        public ViewHolder(RecyclerViewEmployeeItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(QueueEmployeeModel model) {

            binding.role.setText(model.role);
            binding.employeeName.setText(model.name);

            if (model.uri != Uri.EMPTY) {
                binding.employeePhoto.setImageURI(model.uri);
            }
        }
    }
}
