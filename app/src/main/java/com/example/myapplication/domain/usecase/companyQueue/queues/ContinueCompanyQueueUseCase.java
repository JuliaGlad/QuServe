package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class ContinueCompanyQueueUseCase {
    public Completable invoke(String queueId, String companyId){
        return DI.companyQueueRepository.continueCompanyQueue(queueId, companyId);
    }
}
