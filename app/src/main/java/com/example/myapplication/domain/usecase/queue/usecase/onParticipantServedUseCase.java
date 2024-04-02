package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;
import com.google.firebase.firestore.DocumentSnapshot;

import io.reactivex.rxjava3.core.Completable;

public class onParticipantServedUseCase {
    public Completable invoke(String queueId){
        return DI.queueRepository.onParticipantServed(queueId);
    }
}
