package com.example.myapplication.presentation.utils.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.queue.QueueMidTimeModel;

import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MidTimeWorker extends Worker {

    public MidTimeWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        DI.getQueueMidTimeModel.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<QueueMidTimeModel>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull QueueMidTimeModel queueMidTimeModel) {
                        DI.updateMidTimeUseCase.invoke(queueMidTimeModel.getId(), queueMidTimeModel.getMidTime(), queueMidTimeModel.getPassed());
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });


        return Result.success();
    }

}
