package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.profile.UserBooleanDataModel;

import io.reactivex.rxjava3.core.Single;

public class GetUserBooleanDataUseCase {
    public Single<UserBooleanDataModel> invoke(){
        return DI.profileRepository.getUserData().flatMap(userDto ->
                Single.just(new UserBooleanDataModel(userDto.isOwnQueue(), userDto.isParticipateInQueue())));
    }
}
