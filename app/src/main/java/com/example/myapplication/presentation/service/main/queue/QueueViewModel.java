package com.example.myapplication.presentation.service.main.queue;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.domain.model.profile.UserActionsDataModel;
import com.google.firebase.firestore.DocumentSnapshot;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QueueViewModel extends ViewModel {

    private final MutableLiveData<String> _isOwnQueue = new MutableLiveData<>(null);
    LiveData<String> isOwnQueue = _isOwnQueue;

    private final MutableLiveData<String> _isParticipateInQueue = new MutableLiveData<>(null);
    LiveData<String> isParticipateInQueue = _isParticipateInQueue;

    public void getUserData() {
        ProfileDI.getUserActionsDataUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<UserActionsDataModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull UserActionsDataModel userActionsDataModel) {
                        _isOwnQueue.postValue(userActionsDataModel.isOwnQueue());
                        _isParticipateInQueue.postValue(userActionsDataModel.isParticipateInQueue());
                        addSnapshot();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public boolean checkAuthentication() {
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
                            _isOwnQueue.postValue(ProfileDI.getOwnQueueData.invoke(snapshot));
                            _isParticipateInQueue.postValue(ProfileDI.getParticipateInQueueData.invoke(snapshot));
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