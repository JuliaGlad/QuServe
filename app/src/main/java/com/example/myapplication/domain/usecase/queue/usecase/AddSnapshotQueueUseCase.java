package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.di.DI;
import com.google.firebase.firestore.DocumentSnapshot;

import io.reactivex.rxjava3.core.Observable;

public class AddSnapshotQueueUseCase {
    public Observable<DocumentSnapshot> invoke(String path){
        return DI.queueRepository.addSnapshotListener(path);
    }
}
