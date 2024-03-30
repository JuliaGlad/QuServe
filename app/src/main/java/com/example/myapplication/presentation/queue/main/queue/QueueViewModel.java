package com.example.myapplication.presentation.queue.main.queue;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.profile.UserBooleanDataModel;
import com.example.myapplication.domain.model.queue.QueueIdAndNameModel;
import com.google.firebase.firestore.DocumentSnapshot;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QueueViewModel extends ViewModel {

    private String ownerQueueId = null;
    private String participantQueueId = null;

    private final MutableLiveData<Boolean> _isOwnQueue = new MutableLiveData<>(false);
    LiveData<Boolean> isOwnQueue = _isOwnQueue;

    private final MutableLiveData<Boolean> _isParticipateInQueue = new MutableLiveData<>(false);
    LiveData<Boolean> isParticipateInQueue = _isParticipateInQueue;

    public void getUserData() {
        DI.getUserBooleanDataUseCase.invoke()
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
        return DI.checkAuthenticationUseCase.invoke();
    }

    private void addSnapshot() {
        DI.addSnapshotProfileUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DocumentSnapshot>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull DocumentSnapshot snapshot) {
                        if (snapshot != null) {
                            if (DI.checkBooleanDataUseCase.invoke(snapshot, Boolean.TRUE.equals(_isOwnQueue.getValue()), Boolean.TRUE.equals(_isParticipateInQueue.getValue()))) {
                                _isOwnQueue.postValue(DI.getOwnQueueData.invoke(snapshot));
                                _isParticipateInQueue.postValue(DI.getParticipateInQueueData.invoke(snapshot));
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