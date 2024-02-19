package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;
import com.google.firebase.firestore.DocumentSnapshot;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class AddSnapshotUseCase {
    public Observable<DocumentSnapshot> invoke(String queueId){
        return DI.queueRepository.addSnapshotListener(queueId);
    }
}
