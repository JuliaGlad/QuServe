package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class NextCompanyQueueParticipantUpdateListUseCase {

    public Completable invoke(String queueId, String companyId, String name, int passed){
        return DI.companyQueueRepository.nextCompanyQueueParticipantUpdateList(queueId, companyId, name, passed);
    }
}
