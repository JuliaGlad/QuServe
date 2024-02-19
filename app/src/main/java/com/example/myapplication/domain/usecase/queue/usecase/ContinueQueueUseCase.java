package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class ContinueQueueUseCase {
    public Completable invoke(String queueId){
        return DI.queueRepository.continueQueue(queueId);
    }
}
