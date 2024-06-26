package com.example.myapplication.presentation.utils.workers;

import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.myapplication.di.company.CompanyQueueUserDI;

public class ApprovedWorker extends Worker {

    public ApprovedWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String companyId = getInputData().getString(COMPANY_ID);
        CompanyQueueUserDI.updateApprovedUseCase.invoke(companyId);
        return Result.success();
    }
}
