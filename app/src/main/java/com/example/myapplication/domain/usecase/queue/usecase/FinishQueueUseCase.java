package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class FinishQueueUseCase {
    public void invoke(String queueId){
        DI.queueRepository.finishQueue(queueId);
    }
}
