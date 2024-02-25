package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

public class UpdateOwnQueueUseCase {
    public void invoke(boolean value){
        DI.profileRepository.updateOwnQueue(value);
    }
}
