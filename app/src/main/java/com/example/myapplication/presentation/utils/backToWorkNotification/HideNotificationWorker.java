package com.example.myapplication.presentation.utils.backToWorkNotification;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class HideNotificationWorker extends Worker {

    NotificationGoBackToWork service;

    public HideNotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams, NotificationGoBackToWork service) {
        super(context, workerParams);
        this.service = service;
    }

    @NonNull
    @Override
    public Result doWork() {
        service.stopForeground(true);
        service.stopSelf();
        return Result.success();
    }
}
