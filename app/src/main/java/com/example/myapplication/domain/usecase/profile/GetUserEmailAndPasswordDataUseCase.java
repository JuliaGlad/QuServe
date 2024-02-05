package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.UserEmailAndNameModel;

import io.reactivex.rxjava3.core.Single;

public class GetUserEmailAndPasswordDataUseCase {

    public Single<UserEmailAndNameModel> invoke(){
        return DI.profileRepository.getUserData().flatMap(userDto ->
                Single.just(new UserEmailAndNameModel(userDto.getUserName(), userDto.getEmail())));
    }
}
