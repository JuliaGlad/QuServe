package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

public class CheckAuthenticationUseCase {
    public boolean invoke(){
        return DI.profileRepository.checkAuth();
    }
}
