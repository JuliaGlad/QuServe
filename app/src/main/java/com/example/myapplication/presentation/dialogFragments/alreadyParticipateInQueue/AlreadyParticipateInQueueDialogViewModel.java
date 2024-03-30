package com.example.myapplication.presentation.dialogFragments.alreadyParticipateInQueue;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AlreadyParticipateInQueueDialogViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isLeft = new MutableLiveData<>();
    LiveData<Boolean> isLeft = _isLeft;

    public void leaveQueue() {
        DI.getQueueByParticipantIdUseCase.invoke()
                .flatMapCompletable(queueModel -> DI.removeParticipantById.invoke(queueModel.getId()))
                .andThen(DI.updateParticipateInQueueUseCase.invoke(false))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isLeft.postValue(true);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

}