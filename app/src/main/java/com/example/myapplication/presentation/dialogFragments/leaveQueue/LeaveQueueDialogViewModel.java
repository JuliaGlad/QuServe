package com.example.myapplication.presentation.dialogFragments.leaveQueue;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LeaveQueueDialogViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isLeft = new MutableLiveData<>(false);
    LiveData<Boolean> isLeft = _isLeft;

    public void leaveQueue(String queueId) {
        DI.removeParticipantById.invoke(queueId)
                .andThen(DI.updateParticipateInQueueUseCase.invoke(false))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isLeft.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}