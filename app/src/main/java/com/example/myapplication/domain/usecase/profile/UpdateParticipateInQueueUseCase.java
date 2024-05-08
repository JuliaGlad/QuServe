package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateParticipateInQueueUseCase {
    public Completable invoke(String path){
       return ProfileDI.profileRepository.updateParticipateInQueue(path);
    }
}
