package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.profile.ProfileEmployeeDI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateOwnQueueUseCase {
    public Completable invoke(String value){
       return ProfileDI.profileRepository.updateOwnQueue(value);
    }
}
