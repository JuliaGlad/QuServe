package com.example.myapplication.presentation.utils.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BasicQueueMidTimeWorker extends Worker {

    public BasicQueueMidTimeWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        DI.getQueueMidTimeModel.invoke()
                .flatMapCompletable(queueMidTimeModel ->
                        DI.updateMidTimeUseCase.invoke(queueMidTimeModel.getId(), queueMidTimeModel.getMidTime(), queueMidTimeModel.getPassed15Minutes()))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                    }
                });


        return Result.success();
    }

}
