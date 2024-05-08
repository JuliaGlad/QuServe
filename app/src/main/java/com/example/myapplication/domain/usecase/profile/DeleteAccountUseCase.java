package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteAccountUseCase {
    public Completable invoke(String password){
        return ProfileDI.profileRepository.deleteAccount(password);
    }
}
