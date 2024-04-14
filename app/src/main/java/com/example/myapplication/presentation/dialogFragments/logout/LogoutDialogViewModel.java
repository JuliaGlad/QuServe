package com.example.myapplication.presentation.dialogFragments.logout;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.ProfileDI;

public class LogoutDialogViewModel extends ViewModel {

    public void logout(){
        ProfileDI.signOutUseCase.invoke();
    }
}