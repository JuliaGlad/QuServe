package com.example.myapplication.domain.usecase.profile;

import static com.example.myapplication.presentation.utils.Utils.NOT_QUEUE_OWNER;

import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.domain.model.profile.UserActionsDataModel;

import io.reactivex.rxjava3.core.Single;

public class GetUserActionsDataUseCase {
    public Single<UserActionsDataModel> invoke() {
        if (!ProfileDI.profileRepository.isAnonymous()) {
            return ProfileDI.profileRepository.getUserData().flatMap(userDto -> Single.just(new UserActionsDataModel(
                    userDto.isOwnQueue(), userDto.isRestaurantVisitor(), userDto.isParticipateInQueue()))
            );
        } else {
            return ProfileDI.profileRepository.getAnonymousUser().flatMap(anonymousDto -> Single.just(new UserActionsDataModel(
                     NOT_QUEUE_OWNER, anonymousDto.getRestaurantVisitor(), anonymousDto.getParticipateInQueue()
             )));
        }
    }
}
