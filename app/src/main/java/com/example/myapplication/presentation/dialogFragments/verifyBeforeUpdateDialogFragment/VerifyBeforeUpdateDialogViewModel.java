package com.example.myapplication.presentation.dialogFragments.verifyBeforeUpdateDialogFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class VerifyBeforeUpdateDialogViewModel extends ViewModel {
    private final MutableLiveData<Boolean> _isVerified = new MutableLiveData<>();
    LiveData<Boolean> isVerified = _isVerified;

    public void checkVerification(String email, String password) {
        DI.signOutUseCase.invoke();
        DI.signInWithEmailAndPasswordUseCase.invoke(email, password)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        DI.checkVerificationUseCase.invoke()
                                .subscribeOn(Schedulers.io())
                                .subscribe(new SingleObserver<Boolean>() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onSuccess(@NonNull Boolean aBoolean) {
                                        _isVerified.postValue(aBoolean);
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {

                                    }
                                });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }
}