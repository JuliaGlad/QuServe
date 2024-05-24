package com.example.myapplication.presentation.dialogFragments.resetPassword;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileDI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ResetPasswordDialogViewModel extends ViewModel {

    private final MutableLiveData<String> _emailError = new MutableLiveData<>(null);
    LiveData<String> emailError = _emailError;

    private final MutableLiveData<Boolean> _isSend = new MutableLiveData<>(false);
    LiveData<Boolean> isSend = _isSend;

    public void sendResetPasswordEmail(String checkingEmail) {
         ProfileDI.sendResetPasswordEmailUseCase.invoke(checkingEmail)
                 .subscribeOn(Schedulers.io())
                 .subscribe(new SingleObserver<Boolean>() {
                     @Override
                     public void onSubscribe(@NonNull Disposable d) {

                     }

                     @Override
                     public void onSuccess(@NonNull Boolean aBoolean) {
                        _isSend.postValue(aBoolean);
                     }

                     @Override
                     public void onError(@NonNull Throwable e) {

                     }
                 });
    }

    public void sendEmailError(String errorMessage){
        _emailError.setValue(errorMessage);
    }

    public void removeEmailError(){
        _emailError.setValue(null);
    }
}