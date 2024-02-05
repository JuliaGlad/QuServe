package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Single;

public class CheckVerificationUseCase {
    public Single<Boolean> invoke(){
        return DI.profileRepository.checkVerification();
    }
}
