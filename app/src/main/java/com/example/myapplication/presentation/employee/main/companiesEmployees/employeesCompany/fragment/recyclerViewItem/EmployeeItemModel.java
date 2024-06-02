package com.example.myapplication.presentation.employee.main.companiesEmployees.employeesCompany.fragment.recyclerViewItem;

import androidx.fragment.app.Fragment;

import com.example.myapplication.presentation.dialogFragments.changeRole.ChangeRoleDialogFragment;

import java.util.Objects;

import myapplication.android.ui.listeners.ButtonItemListener;
import myapplication.android.ui.recycler.ui.items.items.categoryItem.CategoryItemModel;

public class EmployeeItemModel {
    int id;
    Fragment fragment;
    String name;
    String employeeId;
    String role;
    String companyId;
    boolean isChecked = false;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EmployeeItemModel)) return false;
        EmployeeItemModel that = (EmployeeItemModel) o;
        return id == that.id &&  fragment == that.fragment && Objects.equals(name, that.name) && Objects.equals(employeeId, that.employeeId) && Objects.equals(role, that.role) && Objects.equals(companyId, that.companyId) && Objects.equals(dialogFragment, that.dialogFragment) && Objects.equals(listener, that.listener) && Objects.equals(deleteListener, that.deleteListener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, fragment, employeeId, role, companyId, dialogFragment, listener, deleteListener);
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
