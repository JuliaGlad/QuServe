package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class SendVerificationEmailUseCase {
    public Completable invoke(){
        return DI.profileRepository.sendEmailVerification();
    }
}
