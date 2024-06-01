package com.example.myapplication.presentation.utils.workers;

import static com.example.myapplication.presentation.utils.constants.Utils.PAUSE_AVAILABLE;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class PauseAvailableWorker extends Worker {

    public PauseAvailableWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        PAUSE_AVAILABLE = true;
        return Result.success();
    }
}
