package com.example.myapplication.presentation.utils.workers;

import static com.example.myapplication.presentation.utils.Utils.QUEUE_ID;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BasicQueueFinishWorker extends Worker {

    public BasicQueueFinishWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data = getInputData();
        String queueId = data.getString(QUEUE_ID);
        DI.deleteQrCodeUseCase.invoke(queueId)
                .concatWith(DI.finishQueueUseCase.invoke(queueId))
                .andThen(DI.updateOwnQueueUseCase.invoke(false))
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
