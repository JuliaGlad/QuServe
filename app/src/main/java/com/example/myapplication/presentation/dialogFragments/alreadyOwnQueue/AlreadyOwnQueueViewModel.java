package com.example.myapplication.presentation.dialogFragments.alreadyOwnQueue;

import static com.example.myapplication.presentation.utils.constants.Utils.NOT_QUEUE_OWNER;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.QueueDI;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AlreadyOwnQueueViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isFinished = new MutableLiveData<>();
    LiveData<Boolean> isFinished = _isFinished;

    public void finishQueue(String queueId) {
        QueueDI.deleteQrCodeUseCase.invoke(queueId)
                .concatWith(QueueDI.finishQueueUseCase.invoke(queueId))
                .andThen(ProfileDI.updateOwnQueueUseCase.invoke(NOT_QUEUE_OWNER))
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
}