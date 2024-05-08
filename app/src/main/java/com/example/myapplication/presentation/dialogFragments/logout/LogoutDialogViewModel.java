package com.example.myapplication.presentation.dialogFragments.logout;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileDI;

public class LogoutDialogViewModel extends ViewModel {

    public void logout(){
        ProfileDI.signOutUseCase.invoke();
    }
}