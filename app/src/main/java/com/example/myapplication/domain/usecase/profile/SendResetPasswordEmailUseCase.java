package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;

import io.reactivex.rxjava3.core.Single;

public class SendResetPasswordEmailUseCase {

    public Single<Boolean> invoke(String email){
       return ProfileDI.profileRepository.sendResetPasswordEmail(email);
    }

}
