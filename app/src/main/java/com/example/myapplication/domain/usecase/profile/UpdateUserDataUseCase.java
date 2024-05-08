package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateUserDataUseCase {
    public Completable invoke(String newUserName, String newUserPhone, String newUserGender, String birthday){
        return ProfileDI.profileRepository.updateUserData(newUserName, newUserPhone, newUserGender, birthday);
    }
}
