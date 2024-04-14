package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class ChangePasswordUseCase {
    public Completable invoke(String oldPassword, String newPassword){
        return DI.profileRepository.changePassword(oldPassword, newPassword);
    }
}
