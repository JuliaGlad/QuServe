package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.profile.ProfileDI;

import io.reactivex.rxjava3.core.Completable;

public class CreateAccountUseCase {
    public Completable invoke(String email, String password, String name){
        return ProfileDI.profileRepository.createAccount(email, password, name);
    }
}
