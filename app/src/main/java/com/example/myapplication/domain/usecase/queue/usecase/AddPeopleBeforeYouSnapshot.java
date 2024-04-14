package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Observable;

public class AddPeopleBeforeYouSnapshot {
    public Observable<Integer> invoke(String queueId, int size){
        return DI.queueRepository.addPeopleBeforeDocumentSnapshot(queueId, size);
    }
}
