package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.domain.model.profile.UserActionsDataModel;

import io.reactivex.rxjava3.core.Single;

public class GetUserActionsDataUseCase {
    public Single<UserActionsDataModel> invoke(){
        return ProfileDI.profileRepository.getUserData().flatMap(userDto ->
                Single.just(new UserActionsDataModel(
                        userDto.isOwnQueue(), userDto.isRestaurantVisitor(), userDto.isParticipateInQueue()))
        );
    }
}
