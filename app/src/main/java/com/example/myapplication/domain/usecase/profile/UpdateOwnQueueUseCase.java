package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateOwnQueueUseCase {
    public Completable invoke(boolean value){
       return DI.profileRepository.updateOwnQueue(value);
    }
}
