package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.QueueDI;

public class CheckUserIdUseCase {
    public boolean invoke(){
        return DI.profileRepository.checkUserId();
    }
}
