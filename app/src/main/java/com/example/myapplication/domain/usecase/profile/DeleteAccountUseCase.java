package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteAccountUseCase {
    public Completable invoke(String password){
        return DI.profileRepository.deleteAccount(password);
    }
}
