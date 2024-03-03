package com.example.myapplication.presentation.profile.employees.recyclerViewItem;

import myapplication.android.ui.listeners.ButtonItemListener;

public class EmployeeItemModel {
    int id;
    String name;
    String employeeId;
    String role;
    ButtonItemListener listener;
//    Uri uri;

    public EmployeeItemModel(int id, String name, String employeeId, String role,  ButtonItemListener listener) {
        this.id = id;
        this.name = name;
        this.employeeId = employeeId;
        this.role = role;
        this.listener = listener;
//        this.uri = uri;
    }

//    public Uri getUri(){
//        return uri;
//    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public boolean compareToOther(EmployeeItemModel other){
        return other.id == id;
    }
}
