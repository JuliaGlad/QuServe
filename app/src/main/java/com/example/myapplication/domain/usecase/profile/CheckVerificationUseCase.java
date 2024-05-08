package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;

import io.reactivex.rxjava3.core.Single;

public class CheckVerificationUseCase {
    public Single<Boolean> invoke(){
        return ProfileDI.profileRepository.checkVerification();
    }
}
