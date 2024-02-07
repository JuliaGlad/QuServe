package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.QueueModel;

import io.reactivex.rxjava3.core.Single;

public class GetQueueByParticipantIdUseCase {
    public Single<QueueModel> invoke() {
        return DI.queueRepository.getParticipantsList().flatMap(queueDto ->
                Single.just(new QueueModel( queueDto.getName(), queueDto.getParticipants(), queueDto.getId()))
        );
    }
}
