package com.example.myapplication.presentation.dialogFragments.changeEmail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChangeEmailDialogViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _dismissDialog = new MutableLiveData<>();
    LiveData<Boolean> dismissDialog = _dismissDialog;

    public void checkUser(String password) {
        DI.checkPasswordUseCase.invoke(password)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                       _dismissDialog.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}