package com.example.myapplication.presentation.profile.loggedProfile.main;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;

public class ProfileLoggedFragmentViewModel extends ViewModel {

    public boolean checkAuth() {
        return DI.checkAuthenticationUseCase.invoke();
    }

    public boolean isNull() {
        return DI.isNullUseCase.invoke();
    }

}
