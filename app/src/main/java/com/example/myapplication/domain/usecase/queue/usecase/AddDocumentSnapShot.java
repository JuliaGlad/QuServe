package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.QueueSizeModel;

import io.reactivex.rxjava3.core.Observable;

public class AddDocumentSnapShot {
    public Observable<QueueSizeModel> invoke(String queueId){
       return DI.queueRepository.addParticipantsSizeDocumentSnapshot(queueId).flatMap(integer ->
               Observable.just(new QueueSizeModel(integer)));
    }
}
