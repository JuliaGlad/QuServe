package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateEmailFieldUseCase {
    public Completable invoke(String newEmail){
        return DI.profileRepository.updateEmailField(newEmail);
    }
}
