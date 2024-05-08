package com.example.myapplication.presentation.dialogFragments.stopPause;

import static com.example.myapplication.presentation.utils.Utils.BASIC;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.company.CompanyQueueDI;
import com.example.myapplication.di.QueueDI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class StopPauseViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isStopped = new MutableLiveData<>(false);
    LiveData<Boolean> isStopped = _isStopped;

    public void continueQueue(String queueId, String companyId, String type){
        switch (type){
            case BASIC:
                QueueDI.continueQueueUseCase.invoke(queueId)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                _isStopped.postValue(true);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        });
                break;
            case COMPANY:
                CompanyQueueDI.continueCompanyQueueUseCase.invoke(queueId, companyId)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                _isStopped.postValue(true);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        });
        }

    }
}
