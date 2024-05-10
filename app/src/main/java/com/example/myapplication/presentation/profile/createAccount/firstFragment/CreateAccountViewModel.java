package com.example.myapplication.presentation.profile.createAccount.firstFragment;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileDI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/*
 * @author j.gladkikh
 */
public class CreateAccountViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _verified = new MutableLiveData<>(false);
    LiveData<Boolean> verified = _verified;

    private final MutableLiveData<Boolean> _showDialog = new MutableLiveData<>(false);
    LiveData<Boolean> showDialog = _showDialog;

    private final MutableLiveData<String> _emailError = new MutableLiveData<>(null);

    private final MutableLiveData<String> _passwordError = new MutableLiveData<>(null);

    private final MutableLiveData<String> _nameError = new MutableLiveData<>(null);

    public void createUserWithEmailAndPassword(String email, String password, String userName, Uri uri) {
        ProfileDI.createAccountUseCase.invoke(email, password, userName)
                .concatWith(
                        ProfileDI.uploadProfileImageToFireStorageUseCase.invoke(uri)
                                .andThen(ProfileDI.sendVerificationEmailUseCase.invoke())
                )
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _showDialog.postValue(true);
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
