package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

public class CheckUserIdUseCase {
    public boolean invoke(){
        return DI.profileRepository.checkUserId();
    }
}
