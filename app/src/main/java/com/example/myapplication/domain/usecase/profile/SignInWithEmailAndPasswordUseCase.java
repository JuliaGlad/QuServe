package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class SignInWithEmailAndPasswordUseCase {

    public Completable invoke(String email, String password){
        return DI.profileRepository.signInWithEmailAndPassword(email, password);
    }

}
