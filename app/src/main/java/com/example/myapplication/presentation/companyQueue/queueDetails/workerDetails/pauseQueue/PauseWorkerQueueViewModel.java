package com.example.myapplication.presentation.companyQueue.queueDetails.workerDetails.pauseQueue;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.CompanyQueueDI;
import com.example.myapplication.di.CompanyQueueUserDI;
import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PauseWorkerQueueViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isContinued = new MutableLiveData<>(false);
    LiveData<Boolean> isContinued = _isContinued;

    public void continueQueue(String queueId, String companyId) {
        CompanyQueueDI.continueCompanyQueueUseCase.invoke(queueId, companyId)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isContinued.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}