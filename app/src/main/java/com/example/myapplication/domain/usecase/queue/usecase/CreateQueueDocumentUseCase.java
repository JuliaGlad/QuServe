package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class CreateQueueDocumentUseCase {
    public Completable invoke(String queueID, String queueName, String queueTime){
        return DI.queueRepository.createQueueDocument(queueID, queueName, queueTime);
    }
}
