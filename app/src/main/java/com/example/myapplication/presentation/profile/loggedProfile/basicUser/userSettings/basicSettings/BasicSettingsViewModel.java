package com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.basicSettings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.basicSettings.model.SettingsUserModel;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.basicSettings.state.BasicSettingsState;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BasicSettingsViewModel extends ViewModel {

    private final MutableLiveData<BasicSettingsState> _state = new MutableLiveData<>(new BasicSettingsState.Loading());
    LiveData<BasicSettingsState> state = _state;

    public void retrieveUserData() {
        ProfileDI.getProfileImageUseCase.invoke()
                .zipWith(ProfileDI.getUserEmailAndNameDataUseCase.invoke(), (imageModel, userEmailAndNameModel) ->
                        new SettingsUserModel(userEmailAndNameModel.getName(), userEmailAndNameModel.getEmail(), imageModel.getImageUri()))
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<SettingsUserModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull SettingsUserModel userModel) {
                        _state.postValue(new BasicSettingsState.Success(userModel));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new BasicSettingsState.Error());
                    }
                });
    }

}
