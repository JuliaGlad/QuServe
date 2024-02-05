package com.example.myapplication.presentation.profile.profileLogin;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
/*
 * @author j.gladkikh
 */
public class ProfileNavigationViewModel extends ViewModel {

    private final MutableLiveData<String> _emailError = new MutableLiveData<>(null);
    LiveData<String> emailError = _emailError;

    private final MutableLiveData<String> _passwordError = new MutableLiveData<>(null);
    LiveData<String> passwordError = _passwordError;

    private final MutableLiveData<Boolean> _showProgress = new MutableLiveData<>(false);
    LiveData<Boolean> showProgress = _showProgress;

    private final MutableLiveData<String> _resetEmailError = new MutableLiveData<>(null);
    LiveData<String> resetEmailError = _resetEmailError;

    public void sendResetPasswordEmail(String checkingEmail) {
      DI.sendResetPasswordEmailUseCase.invoke(checkingEmail);
    }

    public void checkCurrentUser(Fragment fragment){
        if (DI.checkAuthentificationUseCase.invoke()){
            NavHostFragment.findNavController(fragment)
                    .navigate(R.id.action_navigation_profile_to_profileLoggedFragment);
        }
    }

    public void signIn(String email, String password, ProfileNavigationFragment fragment) {
        if (!email.isEmpty() && !password.isEmpty()) {
           DI.signInWithEmailAndPasswordUseCase.invoke(email, password)
                   .subscribeOn(Schedulers.io())
                   .subscribe(new CompletableObserver() {
                       @Override
                       public void onSubscribe(@NonNull Disposable d) {

                       }

                       @Override
                       public void onComplete() {
                           NavHostFragment.findNavController(fragment)
                                   .navigate(R.id.action_navigation_profile_to_profileLoggedFragment);
                       }

                       @Override
                       public void onError(@NonNull Throwable e) {
                           Log.e("Exception", e.getMessage());
                       }
                   });
        }
    }

    public void showProgress(boolean showProgress) {
        _showProgress.setValue(showProgress);
    }

    public void sendEmailError(String errorMessage) {
        _emailError.setValue(errorMessage);
        _showProgress.setValue(false);
    }

    public void removeEmailError() {
        _emailError.setValue(null);
    }

    public void sendPasswordError(String errorMessage) {
        _passwordError.setValue(errorMessage);
        _showProgress.setValue(false);
    }

    public void sendResetEmailError(String errorMessage) {
        _resetEmailError.setValue(errorMessage);
    }

    public void removePasswordError() {
        _passwordError.setValue(null);
    }
}
