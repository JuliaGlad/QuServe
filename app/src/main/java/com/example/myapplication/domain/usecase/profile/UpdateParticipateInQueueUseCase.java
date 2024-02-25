package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

public class UpdateParticipateInQueueUseCase {
    public void invoke(boolean value){
        DI.profileRepository.updateParticipateInQueue(value);
    }
}
