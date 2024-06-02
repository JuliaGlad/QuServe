package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.QueueDI;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class AddInProgressDocumentSnapshotUseCase {
    public Observable<String> invoke(String queueId){
        return DI.queueRepository.addInProgressDocumentSnapshot(queueId);
    }
}
