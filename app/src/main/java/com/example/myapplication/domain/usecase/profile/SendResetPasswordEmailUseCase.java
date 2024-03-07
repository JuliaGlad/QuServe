package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Single;

public class SendResetPasswordEmailUseCase {

    public Single<Boolean> invoke(String email){
       return DI.profileRepository.sendResetPasswordEmail(email);
    }

}
