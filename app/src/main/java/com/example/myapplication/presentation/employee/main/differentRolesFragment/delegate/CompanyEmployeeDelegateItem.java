package com.example.myapplication.presentation.employee.main.differentRolesFragment.delegate;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class CompanyEmployeeDelegateItem implements DelegateItem<CompanyEmployeeModel> {

    CompanyEmployeeModel value;

    public CompanyEmployeeDelegateItem(CompanyEmployeeModel value) {
        this.value = value;
    }

    @Override
    public CompanyEmployeeModel content() {
        return value;
    }

    @Override
    public int id() {
        return value.hashCode();
    }

    @Override
    public boolean compareToOther(DelegateItem other) {
        return other.content() == content();
    }
}
