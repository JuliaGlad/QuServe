package com.example.myapplication.presentation.employee.main.companiesEmployees.employeesRestaurant.recycler;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

import myapplication.android.ui.listeners.ButtonItemListener;

public class RestaurantEmployeeItemModel {
    int id;
    String name;
    String role;
    String employeeId;
    ButtonItemListener deleteListener;

    public RestaurantEmployeeItemModel(int id, String name, String role, String employeeId, ButtonItemListener deleteListener) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.employeeId = employeeId;
        this.deleteListener = deleteListener;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public boolean compareTo(RestaurantEmployeeItemModel other){
        return this.hashCode() == other.hashCode();
    }
}
