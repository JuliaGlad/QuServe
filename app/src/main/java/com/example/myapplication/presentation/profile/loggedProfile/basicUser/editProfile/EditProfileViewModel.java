package com.example.myapplication.presentation.profile.loggedProfile.basicUser.editProfile;

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
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.domain.model.profile.UserEditModel;
import com.google.android.gms.tasks.Task;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EditProfileViewModel extends ViewModel {

    private final MutableLiveData<String> _birthday = new MutableLiveData<>();
    LiveData<String> birthday = _birthday;

    private final MutableLiveData<Task<Uri>> _image = new MutableLiveData<>();
    LiveData<Task<Uri>> image = _image;

    private final MutableLiveData<String> _gender = new MutableLiveData<>();
    LiveData<String> gender = _gender;

    private final MutableLiveData<String> _userName = new MutableLiveData<>();
    LiveData<String> userName = _userName;

    private final MutableLiveData<String> _userEmail = new MutableLiveData<>();
    LiveData<String> userEmail = _userEmail;

    private final MutableLiveData<String> _phoneNumber = new MutableLiveData<>();
    LiveData<String> phoneNumber = _phoneNumber;

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
                        _gender.postValue(userEditModel.getGender());
                        _birthday.postValue(userEditModel.getBirthday());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG_EXCEPTION, e.getMessage());
                    }
                });
    }

    public void saveData(String newUserName, String newUserPhoneNumber, String newUserGender, String newBirthday, Uri imageUri, Fragment fragment) {
        DI.updateUserDataUseCase.invoke(newUserName, newUserPhoneNumber, newUserGender, newBirthday)
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

}
