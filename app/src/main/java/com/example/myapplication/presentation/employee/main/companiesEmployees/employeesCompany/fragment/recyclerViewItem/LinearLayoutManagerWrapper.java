package com.example.myapplication.presentation.employee.main.companiesEmployees.employeesCompany.fragment.recyclerViewItem;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

public class LinearLayoutManagerWrapper extends LinearLayoutManager {

    public LinearLayoutManagerWrapper(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}