package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Observable;

public class AddCompanyQueueInProgressSnapshotUseCase {
    public Observable<String> invoke(String queueId, String companyId){
        return DI.companyQueueRepository.addInProgressDocumentSnapshot(companyId, queueId);
    }
}
