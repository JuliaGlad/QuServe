package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;

import io.reactivex.rxjava3.core.Completable;

public class SignInAnonymouslyUseCase {
    public Completable invoke(){
        return ProfileDI.profileRepository.signInAnonymously();
    }
}
