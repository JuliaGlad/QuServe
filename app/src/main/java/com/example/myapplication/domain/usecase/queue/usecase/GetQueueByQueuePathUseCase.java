package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.queue.QueueNameModel;

import io.reactivex.rxjava3.core.Single;

public class GetQueueByQueuePathUseCase {
    public Single<QueueNameModel> invoke(String path) {
        return DI.queueRepository.getQueueByParticipantPath(path).map(dto ->
                new QueueNameModel(dto.getId(), dto.getName()));
    }
}
