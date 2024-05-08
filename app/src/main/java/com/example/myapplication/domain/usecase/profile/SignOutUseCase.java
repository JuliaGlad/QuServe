package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;

public class SignOutUseCase {
    public void invoke(){
        ProfileDI.profileRepository.logout();
    }
}
