package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class CheckPasswordUseCase {
    public Completable invoke(String password){
        return DI.profileRepository.onPasswordCheck(password);
    }
}
