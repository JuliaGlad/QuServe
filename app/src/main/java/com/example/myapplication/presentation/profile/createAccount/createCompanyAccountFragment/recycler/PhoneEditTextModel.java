package com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.recycler;

import myapplication.android.ui.listeners.ResultListener;

public class PhoneEditTextModel {
    public int inputType;
    int id;
    int hint;
    String text;
    ResultListener<String> resultListener;

    public PhoneEditTextModel(int id, int hint, String text, int inputType, boolean editable, ResultListener<String> resultListener) {
        this.id = id;
        this.hint = hint;
        this.inputType = inputType;
        this.text = text;
        this.resultListener = resultListener;
    }


    public void setText(String text) {
        this.text = text;
    }
}
