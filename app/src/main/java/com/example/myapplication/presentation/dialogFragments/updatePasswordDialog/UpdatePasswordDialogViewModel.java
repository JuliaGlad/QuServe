package com.example.myapplication.presentation.dialogFragments.updatePasswordDialog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.ProfileDI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UpdatePasswordDialogViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _dismiss = new MutableLiveData<>();
    LiveData<Boolean> dismiss = _dismiss;

    public void updatePassword(String oldPassword, String newPassword){
        ProfileDI.changePasswordUseCase.invoke(oldPassword, newPassword)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _dismiss.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}