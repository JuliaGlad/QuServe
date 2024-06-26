package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateQueueDataUseCase {
    public Completable invoke(String companyId, String queueId, String name, String location){
        return DI.companyQueueRepository.updateQueueData(companyId, queueId, name, location);
    }
}
