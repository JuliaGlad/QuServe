package com.example.myapplication.presentation.utils;

import static com.example.myapplication.presentation.utils.Utils.QUEUE_ID;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.myapplication.DI;

public class QueueTimeWorker extends Worker {

    public QueueTimeWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data = getInputData();
        DI.finishQueueUseCase.invoke(data.getString(QUEUE_ID));
        return Result.success();
    }
}
