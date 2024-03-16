package com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.recyclerViewItem;

import android.app.Activity;
import android.net.Uri;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Task;

import myapplication.android.ui.listeners.ButtonItemListener;
import myapplication.android.ui.listeners.EmployeeButtonListener;

public class EmployeeItemModel {
    int id;
    Fragment fragment;
    String name;
    String employeeId;
    String role;
    String companyId;
    ButtonItemListener listener;

    public EmployeeItemModel(int id, Fragment fragment, String name, String employeeId, String role, String companyId, ButtonItemListener listener) {
        this.id = id;
        this.fragment = fragment;
        this.name = name;
        this.employeeId = employeeId;
        this.role = role;
        this.companyId = companyId;
        this.listener = listener;
    }


    public int getId() {
        return id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public boolean compareToOther(EmployeeItemModel other){
        return other.id == id;
    }
}
