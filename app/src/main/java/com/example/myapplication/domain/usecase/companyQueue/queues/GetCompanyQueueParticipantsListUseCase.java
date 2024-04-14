package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.queue.QueueParticipantsListModel;

import io.reactivex.rxjava3.core.Single;

public class GetCompanyQueueParticipantsListUseCase {
    public Single<QueueParticipantsListModel> invoke(String queueId, String companyId){
        return DI.companyQueueRepository.getSingleCompanyQueue(companyId, queueId)
                .map(companyQueueDto -> new QueueParticipantsListModel(
                        companyQueueDto.getParticipants(),
                        queueId,
                        companyQueueDto.getInProgress(),
                        companyQueueDto.getPassed()
                ));
    }

}
