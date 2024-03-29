package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class SignInAnonymouslyUseCase {
    public Completable invoke(){
        return DI.profileRepository.signInAnonymously();
    }
}
