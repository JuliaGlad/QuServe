package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;
import com.google.firebase.firestore.DocumentSnapshot;

import io.reactivex.rxjava3.core.Observable;

public class AddSnapshotProfileUseCase {
    public Observable<DocumentSnapshot> invoke(){
        return ProfileDI.profileRepository.addSnapshot();
    }
}
