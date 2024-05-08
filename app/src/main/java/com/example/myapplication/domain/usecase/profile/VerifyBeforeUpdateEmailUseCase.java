package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.profile.ProfileDI;

import io.reactivex.rxjava3.core.Completable;

public class VerifyBeforeUpdateEmailUseCase {
    public Completable invoke(String email){
       return ProfileDI.profileRepository.verifyBeforeUpdateEmail(email);
    }
}
