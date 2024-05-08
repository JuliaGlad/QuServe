package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.domain.model.profile.UserEditModel;


import io.reactivex.rxjava3.core.Single;

public class GetProfileEditUseCase {

    public Single<UserEditModel> invoke(){
        return ProfileDI.profileRepository.getUserData().flatMap(userDto ->
                Single.just(new UserEditModel(
                        userDto.getUserName(),
                        userDto.getGender(),
                        userDto.getPhoneNumber(),
                        userDto.getEmail(),
                        userDto.getBirthday()))
        );
    }

}
