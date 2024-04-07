package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateCompanyQueueMidTimeUseCase {
    public Completable invoke(String queueId, String companyId, int previous, int passed15){
        return DI.companyQueueRepository.updateMidTime(queueId, companyId, previous, passed15);
    }
}
