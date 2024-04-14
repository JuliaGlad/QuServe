package com.example.myapplication.presentation.service.main.queue;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.ProfileDI;
import com.example.myapplication.domain.model.profile.UserBooleanDataModel;
import com.google.firebase.firestore.DocumentSnapshot;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QueueViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isOwnQueue = new MutableLiveData<>(false);
    LiveData<Boolean> isOwnQueue = _isOwnQueue;

    private final MutableLiveData<Boolean> _isParticipateInQueue = new MutableLiveData<>(false);
    LiveData<Boolean> isParticipateInQueue = _isParticipateInQueue;

    public void getUserData() {
        ProfileDI.getUserBooleanDataUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<UserBooleanDataModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull UserBooleanDataModel userBooleanDataModel) {
                        _isOwnQueue.postValue(userBooleanDataModel.isOwnQueue());
                        _isParticipateInQueue.postValue(userBooleanDataModel.isParticipateInQueue());
                        addSnapshot();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public boolean checkAuthentication(){
        return ProfileDI.checkAuthenticationUseCase.invoke();
    }

    private void addSnapshot() {
        ProfileDI.addSnapshotProfileUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DocumentSnapshot>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull DocumentSnapshot snapshot) {
                        if (snapshot != null) {
                            if (ProfileDI.checkBooleanDataUseCase.invoke(snapshot, Boolean.TRUE.equals(_isOwnQueue.getValue()), Boolean.TRUE.equals(_isParticipateInQueue.getValue()))) {
                                _isOwnQueue.postValue(ProfileDI.getOwnQueueData.invoke(snapshot));
                                _isParticipateInQueue.postValue(ProfileDI.getParticipateInQueueData.invoke(snapshot));
                            }
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}