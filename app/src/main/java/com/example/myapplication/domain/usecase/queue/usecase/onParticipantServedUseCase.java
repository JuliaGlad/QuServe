package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class onParticipantServedUseCase {
    public Completable invoke(String path){
        return DI.queueRepository.onParticipantServed(path);
    }
}
