package com.example.myapplication.presentation.employee.main.companiesEmployees.employeesCompany.fragment.recyclerViewItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class EmployeeItemCallBack extends DiffUtil.ItemCallback<EmployeeItemModel> {
    @Override
    public boolean areItemsTheSame(@NonNull EmployeeItemModel oldItem, @NonNull EmployeeItemModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull EmployeeItemModel oldItem, @NonNull EmployeeItemModel newItem) {
        return oldItem.equals(newItem);
    }
}
