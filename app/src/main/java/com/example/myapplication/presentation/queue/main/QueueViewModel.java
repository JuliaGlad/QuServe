package com.example.myapplication.presentation.queue.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.queue.QueueIdAndNameModel;
import com.example.myapplication.domain.model.profile.UserBooleanDataModel;
import com.google.firebase.firestore.DocumentSnapshot;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QueueViewModel extends ViewModel {

    private String queueId = null;

    private final MutableLiveData<Boolean> _isOwnQueue = new MutableLiveData<>();
    LiveData<Boolean> isOwnQueue = _isOwnQueue;

    private final MutableLiveData<Boolean> _isParticipateInQueue = new MutableLiveData<>();
    LiveData<Boolean> isParticipateInQueue = _isParticipateInQueue;

    public void leaveQueue() {
        DI.removeParticipantById.invoke(queueId)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        DI.updateParticipateInQueueUseCase.invoke(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void finishQueue() {
        DI.deleteQrCodeUseCase.invoke(queueId);
        DI.finishQueueUseCase.invoke(queueId)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        DI.updateOwnQueueUseCase.invoke(false);
                        _isOwnQueue.postValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void getQueueData() {
        DI.getQueueByAuthorUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<QueueIdAndNameModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull QueueIdAndNameModel queueIdAndNameModel) {
                        queueId = queueIdAndNameModel.getId();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

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

    private void addSnapshot() {
        DI.addSnapshotProfileUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DocumentSnapshot>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull DocumentSnapshot snapshot) {
                        if (snapshot != null) {
                            if (DI.checkBooleanDataUseCase.invoke(snapshot, Boolean.TRUE.equals(_isOwnQueue.getValue()), Boolean.TRUE.equals(_isParticipateInQueue.getValue()))) {
                                _isOwnQueue.postValue(DI.getOwnQueueData.invoke(snapshot));
                                _isParticipateInQueue.postValue(DI.getParticipateInQueueData.invoke(snapshot));
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}