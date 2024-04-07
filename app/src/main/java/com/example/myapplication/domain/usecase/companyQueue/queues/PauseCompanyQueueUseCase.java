package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class PauseCompanyQueueUseCase {
    public Completable invoke(String queueId, String companyId, int hours, int minutes, int seconds){
        return DI.companyQueueRepository.pauseQueue(queueId, companyId, hours, minutes, seconds);
    }
}
