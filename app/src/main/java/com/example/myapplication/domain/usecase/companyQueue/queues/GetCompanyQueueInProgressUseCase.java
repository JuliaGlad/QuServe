package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.queue.QueueInProgressModel;

import io.reactivex.rxjava3.core.Single;

public class GetCompanyQueueInProgressUseCase {
    public Single<QueueInProgressModel> invoke(String companyId, String queueId){
        return DI.companyQueueRepository.getSingleCompanyQueue(companyId, queueId)
                .map(companyQueueDto -> new QueueInProgressModel(queueId, companyQueueDto.getInProgress()));
    }
}
