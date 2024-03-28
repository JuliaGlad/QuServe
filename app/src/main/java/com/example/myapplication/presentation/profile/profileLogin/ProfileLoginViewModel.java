package com.example.myapplication.presentation.profile.profileLogin;

import static com.example.myapplication.presentation.utils.Utils.BASIC;
import static com.example.myapplication.presentation.utils.Utils.STATE;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.BasicUserFragment;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/*
 * @author j.gladkikh
 */
public class ProfileLoginViewModel extends ViewModel {

    private final MutableLiveData<String> _emailError = new MutableLiveData<>(null);
    LiveData<String> emailError = _emailError;

    private final MutableLiveData<String> _passwordError = new MutableLiveData<>(null);
    LiveData<String> passwordError = _passwordError;
    public void signIn(String email, String password, ProfileLoginFragment fragment) {
        if (!email.isEmpty() && !password.isEmpty()) {
            DI.signInWithEmailAndPasswordUseCase.invoke(email, password)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {

                            Bundle bundle = new Bundle();
                            bundle.putString(STATE, BASIC);

                            FragmentManager fragmentManager = fragment.getActivity().getSupportFragmentManager();

                            fragmentManager.beginTransaction()
                                    .replace(R.id.logged_container, BasicUserFragment.class, bundle)
                                    .commit();

                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.e("Exception", e.getMessage());
                            Snackbar.make(fragment.requireView(), e.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
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
