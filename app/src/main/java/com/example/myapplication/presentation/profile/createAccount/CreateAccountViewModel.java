package com.example.myapplication.presentation.profile.createAccount;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CreateAccountViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _verified = new MutableLiveData<>(false);
    LiveData<Boolean> verified = _verified;

    private final MutableLiveData<String> _emailError = new MutableLiveData<>(null);
    LiveData<String> emailError = _emailError;

    private final MutableLiveData<String> _passwordError = new MutableLiveData<>(null);
    LiveData<String> passwordError = _passwordError;

    private final MutableLiveData<String> _nameError = new MutableLiveData<>(null);
    LiveData<String> nameError = _nameError;

    public void createUserWithEmailAndPassword(String email, String password, String userName, String phoneNumber) {

        DI.createAccountUseCase.invoke(email, password, userName, phoneNumber)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("Complete creation", "completed");
                        DI.sendVerificationEmailUseCase.invoke().subscribeOn(Schedulers.io())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        Log.d("Complete send", "completed");
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {

                                    }
                                });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("Exception", e.getMessage());
                    }
                });
    }

    public void deleteAccount() {
        DI.deleteAccountUseCase.invoke();
    }

    public void checkVerification() {
        DI.checkVerificationUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Boolean aBoolean) {
                        _verified.postValue(aBoolean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void sendEmailError(String errorMessage) {
        _emailError.setValue(errorMessage);
    }

    public void removeEmailError() {
        _emailError.setValue(null);
    }

    public void sendPasswordError(String errorMessage) {
        _passwordError.setValue(errorMessage);
    }

    public void removePasswordError() {
        _passwordError.setValue(null);
    }

    public void sendNameError(String errorMessage) {
        _nameError.setValue(errorMessage);
    }

    public void removeNameError() {
        _nameError.setValue(null);
    }

}
