package com.example.myapplication.presentation.profile.loggedProfile.delegates.companyListItem;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

import myapplication.android.ui.listeners.ButtonItemListener;

public class CompanyListItemDelegateModel {
    int id;
    String name;
    Task<Uri> uriTask;
    ButtonItemListener listener;

    public CompanyListItemDelegateModel(int id, String name, Task<Uri> uriTask, ButtonItemListener listener) {
        this.id = id;
        this.name = name;
        this.uriTask = uriTask;
        this.listener = listener;
    }
}
