package com.example.myapplication.presentation.dialogFragments.finishQueue;

import static com.example.myapplication.presentation.utils.Utils.BASIC;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.NOT_QUEUE_OWNER;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.company.CompanyQueueDI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.QueueDI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FinishQueueViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isFinished = new MutableLiveData<>(false);
    LiveData<Boolean> isFinished = _isFinished;

    public void finishQueue(String queueId, String type, String companyId) {
        switch (type){
            case BASIC:
                QueueDI.deleteQrCodeUseCase.invoke(queueId)
                        .concatWith(QueueDI.finishQueueUseCase.invoke(queueId))
                        .andThen(ProfileDI.updateOwnQueueUseCase.invoke(NOT_QUEUE_OWNER))
                        .subscribeOn(Schedulers.io())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                _isFinished.postValue(true);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        });
                break;
            case COMPANY:
                QueueDI.deleteQrCodeUseCase.invoke(queueId)
                        .concatWith(CompanyQueueDI.deleteCompanyQueueUseCase.invoke(companyId, queueId))
                        .subscribeOn(Schedulers.io())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                _isFinished.postValue(true);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        });
                break;
        }

    }

}
