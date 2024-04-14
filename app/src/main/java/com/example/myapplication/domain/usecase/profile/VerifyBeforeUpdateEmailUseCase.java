package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class VerifyBeforeUpdateEmailUseCase {
    public Completable invoke(String email){
       return DI.profileRepository.verifyBeforeUpdateEmail(email);
    }
}
