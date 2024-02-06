package com.example.myapplication.presentation.profile.profileSettings;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.ImageModel;
import com.example.myapplication.domain.model.UserEmailAndNameModel;
import com.google.android.gms.tasks.Task;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SettingsViewModel extends ViewModel {

    private final MutableLiveData<Task<Uri>> _image = new MutableLiveData<>();
    LiveData<Task<Uri>> image = _image;

    private final MutableLiveData<String> _userName = new MutableLiveData<>();
    LiveData<String> userName = _userName;

    private final MutableLiveData<String> _userEmail = new MutableLiveData<>();
    LiveData<String> userEmail = _userEmail;

    private final MutableLiveData<String> _helpUserEmail = new MutableLiveData<>();
    LiveData<String> helpUserEmail = _helpUserEmail;

    private final MutableLiveData<Boolean> _isSuccessful = new MutableLiveData<>();
    LiveData<Boolean> isSuccessful = _isSuccessful;

    private final MutableLiveData<Boolean> _verified = new MutableLiveData<>();
    LiveData<Boolean> verified = _verified;

    private final MutableLiveData<Boolean> _updatePassword = new MutableLiveData<>();
    LiveData<Boolean> updatePassword = _updatePassword;

    private final MutableLiveData<Boolean> _updateEmailField = new MutableLiveData<>();
    LiveData<Boolean> updateEmailField = _updateEmailField;

    public void updatePassword(String oldPassword, String newPassword) {
        if (!oldPassword.isEmpty() && !newPassword.isEmpty()) {
//            authCredential = EmailAuthProvider.getCredential(authUser.getEmail(), oldPassword);
//            authUser.reauthenticate(authCredential).addOnSuccessListener(unused ->
//                    _updatePassword.postValue(authUser.updatePassword(newPassword)));
            DI.changePasswordUseCase.invoke(oldPassword, newPassword)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            _updatePassword.postValue(true);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            _updatePassword.postValue(false);
                        }
                    });
        }
    }

    public void changeEmail(String email) {
        if (!email.isEmpty()) {
            DI.verifyBeforeUpdateEmailUseCase.invoke(email)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            Log.e("Complete Sending", "complete");
                            _isSuccessful.postValue(true);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.e("Change email exception", e.getMessage());
                        }
                    });
        }
    }

    public void deleteAccount() {
        DI.deleteAccountUseCase.invoke();
    }

    public void updateEmailField(String newEmail, String password) {
        DI.signOutUseCase.invoke();
        DI.signInWithEmailAndPasswordUseCase.invoke(newEmail, password)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        DI.updateEmailFieldUseCase.invoke(newEmail)
                                .subscribeOn(Schedulers.io())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        _updateEmailField.postValue(true);
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        _updateEmailField.postValue(false);
                                        Log.e("Exception", e.getMessage());
                                    }
                                });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("Exception", e.getMessage());
                    }
                });
    }

    public void checkVerification(String newEmail, String password) {
        DI.signInWithEmailAndPasswordUseCase.invoke(newEmail, password)
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
                                        _verified.postValue(aBoolean);
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Log.e("Exception", e.getMessage());
                                    }
                                });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void retrieveUserData() {
        DI.getUserEmailAndPasswordDataUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<UserEmailAndNameModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull UserEmailAndNameModel userEmailAndNameModel) {
                        _userName.postValue(userEmailAndNameModel.getName());
                        _userEmail.postValue(userEmailAndNameModel.getEmail());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("Exception", e.getMessage());
                    }
                });
    }

    public void getProfileImage() {
        DI.getProfileImageUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ImageModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ImageModel imageModel) {
                        _image.postValue(imageModel.getImageUri());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("Exception", e.getMessage());
                    }
                });
    }

    public void logout() {
        DI.signOutUseCase.invoke();
    }

}
