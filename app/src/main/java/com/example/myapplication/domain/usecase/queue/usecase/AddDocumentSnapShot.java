package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.QueueSizeModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class AddDocumentSnapShot {
    public Observable<QueueSizeModel> invoke(String queueId){
       return DI.queueRepository.addDocumentSnapshot(queueId).flatMap(integer ->
               Observable.just(new QueueSizeModel(integer)));
    }
}
