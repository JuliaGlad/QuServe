package com.example.myapplication.presentation.queue.queueDetails.pausedQueueFragment;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.QueueIdAndNameModel;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PausedQueueFragmentViewModel extends ViewModel {

    private String queueId;

    public void getQueue(){
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

    public Completable continueQueue(){
       return DI.continueQueueUseCase.invoke(queueId);
    }
}
