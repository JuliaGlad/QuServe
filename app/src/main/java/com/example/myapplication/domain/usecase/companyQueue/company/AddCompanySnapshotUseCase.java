package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.DI;
import com.google.firebase.firestore.DocumentSnapshot;

import io.reactivex.rxjava3.core.Observable;

public class AddCompanySnapshotUseCase {
    public Observable<DocumentSnapshot> invoke(String companyId){
        return DI.companyUserRepository.addSnapshot(companyId);
    }
}
