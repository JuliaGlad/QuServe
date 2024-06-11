package com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.privacySettings;

import static com.example.myapplication.presentation.utils.constants.Utils.NOT_PARTICIPATE_IN_QUEUE;
import static com.example.myapplication.presentation.utils.constants.Utils.NOT_QUEUE_OWNER;
import static com.example.myapplication.presentation.utils.constants.Utils.NOT_RESTAURANT_VISITOR;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.domain.model.profile.UserActionsDataModel;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PrivacySettingsViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _haveActions = new MutableLiveData<>(null);
    LiveData<Boolean> haveActions = _haveActions;

    private final MutableLiveData<Boolean> _openSuccessDialog = new MutableLiveData<>();
    LiveData<Boolean> openSuccessDialog = _openSuccessDialog;

    private final MutableLiveData<Boolean> _openVerifyDialog = new MutableLiveData<>();
    LiveData<Boolean> openVerifyDialog = _openVerifyDialog;

    public void verifyBeforeUpdate(String email){
        ProfileDI.verifyBeforeUpdateEmailUseCase.invoke(email)
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
        ProfileDI.updateEmailFieldUseCase.invoke(email)
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

    public void getUserActions(){
        ProfileDI.getUserActionsDataUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<UserActionsDataModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull UserActionsDataModel userActionsDataModel) {
                        if (!userActionsDataModel.getRestaurantVisitor().equals(NOT_RESTAURANT_VISITOR) || !userActionsDataModel.isOwnQueue().equals(NOT_QUEUE_OWNER) || !userActionsDataModel.isParticipateInQueue().equals(NOT_PARTICIPATE_IN_QUEUE)){
                            _haveActions.postValue(true);
                        } else {
                            _haveActions.postValue(false);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}