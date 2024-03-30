package com.example.myapplication.presentation.dialogFragments.pauseQueue;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PauseQueueViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isPaused = new MutableLiveData<>();
    LiveData<Boolean> isPaused = _isPaused;

    public void pauseQueue(int hours, int minutes, int seconds, String queueId) {
        Log.d("QueueId", queueId);
        DI.pauseQueueUseCase.invoke(queueId, hours, minutes, seconds)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isPaused.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}
