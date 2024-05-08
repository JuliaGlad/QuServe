package com.example.myapplication.presentation.dialogFragments.resetPassword;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileDI;

import io.reactivex.rxjava3.core.Single;

public class ResetPasswordDialogViewModel extends ViewModel {

    private final MutableLiveData<String> _emailError = new MutableLiveData<>(null);
    LiveData<String> emailError = _emailError;

    public Single<Boolean> sendResetPasswordEmail(String checkingEmail) {
        return ProfileDI.sendResetPasswordEmailUseCase.invoke(checkingEmail);
    }

    public void sendEmailError(String errorMessage){
        _emailError.setValue(errorMessage);
    }

    public void removeEmailError(){
        _emailError.setValue(null);
    }
}