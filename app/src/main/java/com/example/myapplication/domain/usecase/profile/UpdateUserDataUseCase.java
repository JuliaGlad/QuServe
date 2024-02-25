package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateUserDataUseCase {
    public Completable invoke(String newUserName,  String newUserPhone, String newUserGender){
        return (DI.profileRepository.updateUserData(newUserName, newUserPhone, newUserGender));
    }
}
