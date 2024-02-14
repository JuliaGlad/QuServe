package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class NextParticipantUseCase {
    public void invoke(String queueID, String name){
         DI.queueRepository.nextParticipant(queueID, name);
    }
}
