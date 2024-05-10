package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.data.dto.user.UserDto;
import com.example.myapplication.di.profile.ProfileDI;

import io.reactivex.rxjava3.core.Single;

public class GetParticipantQueuePathUseCase {
    public Single<String> invoke(){
        return ProfileDI.profileRepository.getUserData().map(UserDto::isParticipateInQueue);
    }
}
