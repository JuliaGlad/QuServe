package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.queue.QueueSizeModel;

import io.reactivex.rxjava3.core.Observable;

public class AddCompanyQueueParticipantsSizeSnapshot {
    public Observable<QueueSizeModel> invoke(String companyId, String queueId){
        return DI.companyQueueRepository.addParticipantsSizeDocumentSnapshot(companyId, queueId).flatMap(integer ->
                Observable.just(new QueueSizeModel(integer)));
    }
}
