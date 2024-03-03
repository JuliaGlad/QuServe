package com.example.myapplication.presentation.profile.employees.recyclerViewItem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewEmployeeItemBinding;

public class EmployeeItemAdapter extends ListAdapter<EmployeeItemModel, RecyclerView.ViewHolder> {

    public EmployeeItemAdapter() {
        super(new EmployeeItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewEmployeeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewEmployeeItemBinding binding;

        public ViewHolder( RecyclerViewEmployeeItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(EmployeeItemModel model){
            binding.employeeName.setText(model.getName());
            binding.role.setText(model.role);

            binding.employeeItem.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }
    }
}
