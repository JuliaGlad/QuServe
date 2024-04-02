package com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.recyclerViewItem;

import androidx.fragment.app.Fragment;

import com.example.myapplication.presentation.dialogFragments.changeRole.ChangeRoleDialogFragment;

import myapplication.android.ui.listeners.ButtonItemListener;

public class EmployeeItemModel {
    int id;
    Fragment fragment;
    String name;
    String employeeId;
    String role;
    String companyId;
    ChangeRoleDialogFragment dialogFragment;
    ButtonItemListener listener;
    ButtonItemListener deleteListener;

    public EmployeeItemModel(int id, Fragment fragment, String name, String employeeId, String role, String companyId, ChangeRoleDialogFragment dialogFragment, ButtonItemListener listener, ButtonItemListener deleteListener) {
        this.id = id;
        this.fragment = fragment;
        this.name = name;
        this.employeeId = employeeId;
        this.role = role;
        this.companyId = companyId;
        this.listener = listener;
        this.deleteListener = deleteListener;
        this.dialogFragment = dialogFragment;
    }

    public ButtonItemListener getDeleteListener() {
        return deleteListener;
    }

    public ChangeRoleDialogFragment getDialogFragment() {
        return dialogFragment;
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
