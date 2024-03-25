package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class FinishCompanyQueueUseCase {
    public Completable invoke(String companyId, String queueId){
        return DI.companyQueueRepository.finishQueue(companyId, queueId);
    }
}
