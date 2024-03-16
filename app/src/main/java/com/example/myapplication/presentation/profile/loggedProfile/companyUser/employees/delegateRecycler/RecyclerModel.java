package com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.delegateRecycler;

import com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.recyclerViewItem.EmployeeItemAdapter;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.recyclerViewItem.EmployeeItemModel;

import java.util.List;

public class RecyclerModel {
    int id;
    List<EmployeeItemModel> list;
    EmployeeItemAdapter adapter;

    public RecyclerModel(int id, List<EmployeeItemModel> list, EmployeeItemAdapter adapter) {
        this.id = id;
        this.list = list;
        this.adapter = adapter;
    }

    public int getId() {
        return id;
    }

    public List<EmployeeItemModel> getList() {
        return list;
    }

    public EmployeeItemAdapter getAdapter() {
        return adapter;
    }
}
