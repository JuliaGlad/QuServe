package com.example.myapplication.presentation.profile.loggedProfile.basicUser.editProfile;

import androidx.lifecycle.ViewModel;

import static com.example.myapplication.presentation.utils.Utils.TAG_EXCEPTION;

import android.net.Uri;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.domain.model.profile.UserEditModel;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.basicUserStateAndModel.BasicUserState;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.editProfile.model.EditBasicUserState;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.editProfile.model.EditUserModel;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EditProfileViewModel extends ViewModel {

    private final MutableLiveData<EditBasicUserState> _state = new MutableLiveData<>(new EditBasicUserState.Loading());
    LiveData<EditBasicUserState> state = _state;

    public void retrieveUserData() {
        DI.getProfileEditUseCase.invoke()
                .zipWith(DI.getProfileImageUseCase.invoke(), (userEditModel, imageModel) -> new EditUserModel(
                        userEditModel.getPhoneNumber(),
                        userEditModel.getUserName(),
                        userEditModel.getBirthday(),
                        userEditModel.getEmail(),
                        userEditModel.getGender(),
                        imageModel.getImageUri()
                ))
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<EditUserModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull EditUserModel editUserModel) {
                        _state.postValue(new EditBasicUserState.Success(editUserModel));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void saveData(String newUserName, String newUserPhoneNumber, String newUserGender, String newBirthday, Uri imageUri, Fragment fragment) {
        DI.updateUserDataUseCase.invoke(newUserName, newUserPhoneNumber, newUserGender, newBirthday)
                .concatWith(DI.uploadProfileImageToFireStorageUseCase.invoke(imageUri))
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

                    }
                });

    }

}
