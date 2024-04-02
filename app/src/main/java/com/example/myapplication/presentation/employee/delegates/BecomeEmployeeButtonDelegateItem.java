package com.example.myapplication.presentation.employee.delegates;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class BecomeEmployeeButtonDelegateItem implements DelegateItem<BecomeEmployeeButtonModel> {

    BecomeEmployeeButtonModel value;

    public BecomeEmployeeButtonDelegateItem(BecomeEmployeeButtonModel value) {
        this.value = value;
    }

    @Override
    public BecomeEmployeeButtonModel content() {
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
