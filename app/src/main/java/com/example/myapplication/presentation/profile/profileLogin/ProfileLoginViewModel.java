package com.example.myapplication.presentation.profile.profileLogin;

import static com.example.myapplication.presentation.utils.constants.Utils.SIGNED_IN;

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
public class ProfileLoginViewModel extends ViewModel {

    private final MutableLiveData<String> _signedIn = new MutableLiveData<>(null);
    LiveData<String> signedIn = _signedIn;

    private final MutableLiveData<String> _emailError = new MutableLiveData<>(null);
    LiveData<String> emailError = _emailError;

    private final MutableLiveData<String> _passwordError = new MutableLiveData<>(null);
    LiveData<String> passwordError = _passwordError;

    public boolean checkAnonymousActionsUseCase(){
        return ProfileDI.checkAnonymousActionsUseCase.invoke();
    }

    public void signIn(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            ProfileDI.signInWithEmailAndPasswordUseCase.invoke(email, password)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            _signedIn.postValue(SIGNED_IN);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            _signedIn.postValue(e.getMessage());
                        }
                    });
        }
    }

    public void sendEmailError(String errorMessage) {
        _emailError.setValue(errorMessage);
    }

    public void sendPasswordError(String errorMessage) {
        _passwordError.setValue(errorMessage);
    }

    public void removePasswordError() {
        _passwordError.setValue(null);
    }

    public void removeEmailError() {
        _emailError.setValue(null);
    }
}
