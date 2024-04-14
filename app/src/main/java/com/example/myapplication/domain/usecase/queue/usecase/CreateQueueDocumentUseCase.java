package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Single;

public class CreateQueueDocumentUseCase {
    public Single<String> invoke(String queueID, String queueName, String queueTime){
        return DI.queueRepository.createQueueDocument(queueID, queueName, queueTime);
    }
}
