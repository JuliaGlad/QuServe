package com.example.myapplication.presentation.dialogFragments.alreadyOwnQueue;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.queue.QueueIdAndNameModel;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AlreadyOwnQueueViewModel extends ViewModel {

    private String queueId;

    private final MutableLiveData<Boolean> _isFinished = new MutableLiveData<>();
    LiveData<Boolean> isFinished = _isFinished;

    public void finishQueue() {
        DI.deleteQrCodeUseCase.invoke(queueId)
                .concatWith(DI.finishQueueUseCase.invoke(queueId))
                .andThen(DI.updateOwnQueueUseCase.invoke(false))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isFinished.postValue(true);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

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

}