package com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.privacySettings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.basicSettings.state.BasicSettingsState;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.privacySettings.state.PrivacySettingState;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceRedItem.ServiceRedItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceRedItem.ServiceRedItemModel;

import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class PrivacySettingsViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _openSuccessDialog = new MutableLiveData<>();
    LiveData<Boolean> openSuccessDialog = _openSuccessDialog;

    private final MutableLiveData<Boolean> _openVerifyDialog = new MutableLiveData<>();
    LiveData<Boolean> openVerifyDialog = _openVerifyDialog;

    public void verifyBeforeUpdate(String email){
        DI.verifyBeforeUpdateEmailUseCase.invoke(email)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                     _openVerifyDialog.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void updateEmailField(String email){
        DI.updateEmailFieldUseCase.invoke(email)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _openSuccessDialog.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

}