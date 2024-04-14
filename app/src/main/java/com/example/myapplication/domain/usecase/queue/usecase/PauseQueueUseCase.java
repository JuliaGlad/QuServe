package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class PauseQueueUseCase {
    public Completable invoke(String queueId, int hours, int minutes, int seconds){
       return DI.queueRepository.pauseQueue(queueId, hours, minutes, seconds);
    }
}
