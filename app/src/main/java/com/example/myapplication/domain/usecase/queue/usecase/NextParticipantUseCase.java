package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class NextParticipantUseCase {
    public Completable invoke(String queueID, String name){
        return DI.queueRepository.nextParticipantUpdateList(queueID, name);
    }
}
