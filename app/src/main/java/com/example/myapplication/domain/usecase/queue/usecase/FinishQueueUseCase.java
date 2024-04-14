package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class FinishQueueUseCase {
    public Completable invoke(String queueId){
        return DI.queueRepository.finishQueue(queueId);
    }
}
