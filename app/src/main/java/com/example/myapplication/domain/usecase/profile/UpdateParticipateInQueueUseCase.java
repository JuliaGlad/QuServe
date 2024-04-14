package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateParticipateInQueueUseCase {
    public Completable invoke(String path, boolean value){
       return DI.profileRepository.updateParticipateInQueue(path, value);
    }
}
