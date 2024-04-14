package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateUserDataUseCase {
    public Completable invoke(String newUserName, String newUserPhone, String newUserGender, String birthday){
        return DI.profileRepository.updateUserData(newUserName, newUserPhone, newUserGender, birthday);
    }
}
