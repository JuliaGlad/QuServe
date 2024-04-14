package com.example.myapplication.presentation.profile.loggedProfile.main;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.ProfileDI;

public class ProfileLoggedFragmentViewModel extends ViewModel {

    public boolean checkAuth() {
        return ProfileDI.checkAuthenticationUseCase.invoke();
    }

    public boolean isNull() {
        return ProfileDI.isNullUseCase.invoke();
    }

}
