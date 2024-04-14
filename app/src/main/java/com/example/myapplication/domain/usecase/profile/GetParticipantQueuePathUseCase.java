package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.data.dto.UserDto;

import io.reactivex.rxjava3.core.Single;

public class GetParticipantQueuePathUseCase {
    public Single<String> invoke(){
        return DI.profileRepository.getUserData().map(UserDto::getParticipantQueuePath);
    }
}
