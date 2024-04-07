package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateMidTimeUseCase {
    public Completable invoke(String queueId, int previous, int passed){
        return DI.queueRepository.updateMidTime(queueId, previous, passed);
    }
}
