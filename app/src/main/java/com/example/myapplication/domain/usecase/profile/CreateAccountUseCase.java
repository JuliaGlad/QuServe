package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class CreateAccountUseCase {
    public Completable invoke(String email, String password, String name, String phoneNumber){
        return DI.profileRepository.createAccount(email, password, name, phoneNumber);
    }
}
