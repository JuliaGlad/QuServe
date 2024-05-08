package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.domain.model.profile.UserEmailAndNameModel;

import io.reactivex.rxjava3.core.Single;

public class GetUserEmailAndNameDataUseCase {

    public Single<UserEmailAndNameModel> invoke(){
        return ProfileDI.profileRepository.getUserData().flatMap(userDto ->
                Single.just(new UserEmailAndNameModel(userDto.getUserName(), userDto.getEmail())));
    }
}
