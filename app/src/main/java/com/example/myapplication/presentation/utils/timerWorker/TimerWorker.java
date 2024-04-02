package com.example.myapplication.presentation.utils.timerWorker;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.myapplication.DI;

import java.util.Locale;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TimerWorker extends Worker {

    public TimerWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        return null;
    }

//    long timeMillis;
//    ProgressBar progressBar;
//    String timeLeft;
//    TextView textView;
//
//    public TimerWorker(@NonNull Context context, @NonNull WorkerParameters workerParams, long timeMillis, ProgressBar progressBar, String timeLeft, TextView textView) {
//        super(context, workerParams);
//        this.timeMillis = timeMillis;
//        this.progressBar = progressBar;
//        this.textView = textView;
//        this.timeLeft = timeLeft;
//    }
//
//    @NonNull
//    @Override
//    public Result doWork() {
//
//
//
//        return Result.success();
//    }
//
//    private void startCountDown() {
//        new CountDownTimer(timeMillis, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                timeMillis = millisUntilFinished;
//                progressBar.incrementProgressBy(300);
//                updateTimer();
//            }
//
//            @Override
//            public void onFinish() {
//                continueQueue(queueId);
//            }
//        }.start();
//    }
//
//    private void updateTimer() {
//        long hours = (timeMillis / 1000) / 3600;
//        long minutes = ((timeMillis / 1000) % 3600) / 60;
//        long seconds = (timeMillis / 1000) % 60;
//
//        timeLeft = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
//        textView.setText(timeLeft);
//    }
//
//    public void continueQueue(String queueId){
//        DI.continueQueueUseCase.invoke(queueId)
//                .subscribeOn(Schedulers.io())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        _isContinued.postValue(true);
//                    }
//
//                    @Override
//                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//
//                    }
//                });
//    }
}
