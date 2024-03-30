package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateParticipateInQueueUseCase {
    public Completable invoke(boolean value){
       return DI.profileRepository.updateParticipateInQueue(value);
    }
}
