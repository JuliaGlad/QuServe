package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateEmailFieldUseCase {
    public Completable invoke(String newEmail){
        return ProfileDI.profileRepository.updateEmailField(newEmail);
    }
}
