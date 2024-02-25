package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;
import com.google.firebase.firestore.DocumentSnapshot;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class AddSnapshotProfileUseCase {
    public Observable<DocumentSnapshot> invoke(){
        return DI.profileRepository.addSnapshot();
    }
}
