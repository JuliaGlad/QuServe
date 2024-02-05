package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

public class SignOutUseCase {
    public void invoke(){
        DI.profileRepository.logout();
    }
}
