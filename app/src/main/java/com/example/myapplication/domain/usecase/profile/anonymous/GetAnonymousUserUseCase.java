package com.example.myapplication.domain.usecase.profile.anonymous;

import android.util.Log;

import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.domain.model.profile.AnonymousUserModel;

import io.reactivex.rxjava3.core.Single;

public class GetAnonymousUserUseCase {
    public Single<AnonymousUserModel> invoke() {
        return ProfileDI.profileRepository.getAnonymousUser().map(anonymousUserDto ->
                new AnonymousUserModel(
                        anonymousUserDto.getRestaurantVisitor(),
                        anonymousUserDto.getParticipateInQueue())
        );
    }
}
