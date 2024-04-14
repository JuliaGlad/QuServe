package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;

public class SignOutUseCase {
    public void invoke(){
        DI.profileRepository.logout();
    }
}
