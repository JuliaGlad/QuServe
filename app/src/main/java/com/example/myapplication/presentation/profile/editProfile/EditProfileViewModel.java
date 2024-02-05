package com.example.myapplication.presentation.profile.editProfile;

import androidx.lifecycle.ViewModel;

import static com.example.myapplication.presentation.utils.Utils.FEMALE_KEY;
import static com.example.myapplication.presentation.utils.Utils.MALE_KEY;
import static com.example.myapplication.presentation.utils.Utils.TAG_EXCEPTION;

import android.net.Uri;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.JpgImageModel;
import com.example.myapplication.domain.model.UserEditModel;
import com.google.android.gms.tasks.Task;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EditProfileViewModel extends ViewModel {

    private final MutableLiveData<Task<Uri>> _image = new MutableLiveData<>();
    LiveData<Task<Uri>> image = _image;

    private final MutableLiveData<Boolean> _showProgress = new MutableLiveData<>(false);
    LiveData<Boolean> showProgress = _showProgress;

    private final MutableLiveData<String> _userName = new MutableLiveData<>();
    LiveData<String> userName = _userName;

    private final MutableLiveData<String> _userEmail = new MutableLiveData<>();
    LiveData<String> userEmail = _userEmail;

    private final MutableLiveData<String> _phoneNumber = new MutableLiveData<>();
    LiveData<String> phoneNumber = _phoneNumber;

    private final MutableLiveData<Boolean> _femaleMode = new MutableLiveData<>();
    LiveData<Boolean> femaleMode = _femaleMode;

    private final MutableLiveData<Boolean> _maleMode = new MutableLiveData<>();
    LiveData<Boolean> maleMode = _maleMode;

    public void getProfileImage() {
        DI.getProfileImageUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<JpgImageModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull JpgImageModel jpgImageModel) {
                        _image.postValue(jpgImageModel.getImageUri());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG_EXCEPTION, e.getMessage());
                    }
                });
    }

    public void retrieveUserData() {
        DI.getProfileEditUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<UserEditModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull UserEditModel userEditModel) {
                        _userName.postValue(userEditModel.getUserName());
                        _userEmail.postValue(userEditModel.getEmail());
                        _phoneNumber.postValue(userEditModel.getPhoneNumber());

                        try {
                            if (userEditModel.getGender().equals(FEMALE_KEY)) {
                                _femaleMode.postValue(true);
                            } else if (userEditModel.getGender().equals(MALE_KEY)) {
                                _maleMode.postValue(true);
                            }
                        } catch (NullPointerException e) {
                            Log.d(TAG_EXCEPTION, e.getMessage());
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG_EXCEPTION, e.getMessage());
                    }
                });
    }

    public void saveData(String newUserName, String newUserPhoneNumber, String newUserEmail, String newUserGender, Uri imageUri, Fragment fragment) {

        DI.updateUserDataUseCase.invoke(newUserName, newUserEmail, newUserPhoneNumber, newUserGender)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        if (imageUri != null) {
                            DI.uploadToFireStorageUseCase.invoke(imageUri)
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(new CompletableObserver() {
                                        @Override
                                        public void onSubscribe(@NonNull Disposable d) {

                                        }

                                        @Override
                                        public void onComplete() {
                                            fragment.requireActivity().finish();
                                        }

                                        @Override
                                        public void onError(@NonNull Throwable e) {
                                            Log.e("Exception", e.getMessage());
                                        }
                                    });
                        } else {
                            fragment.requireActivity().finish();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    public void showProgress(boolean showProgress) {
        _showProgress.setValue(showProgress);
    }
}
