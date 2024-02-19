package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class PauseQueueUseCase {
    public Completable invoke(String queueId, String time){
       return DI.queueRepository.pauseQueue(queueId, time);
    }
}
