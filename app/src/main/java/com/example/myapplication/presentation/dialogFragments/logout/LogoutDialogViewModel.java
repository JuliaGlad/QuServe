package com.example.myapplication.presentation.dialogFragments.logout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;

public class LogoutDialogViewModel extends ViewModel {

    public void logout(){
        DI.signOutUseCase.invoke();
    }

}