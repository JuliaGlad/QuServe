package com.example.myapplication.presentation.employee.main.differentRolesFragment.delegate;

import myapplication.android.ui.listeners.ButtonItemListener;

public class CompanyEmployeeModel {
    int id;
    String companyId;
    String companyName;
    String role;
    ButtonItemListener listener;

    public CompanyEmployeeModel(int id, String companyId, String companyName, String role, ButtonItemListener listener) {
        this.id = id;
        this.companyId = companyId;
        this.companyName = companyName;
        this.role = role;
        this.listener = listener;
    }
}
