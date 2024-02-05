package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

public class SendResetPasswordEmailUseCase {

    public void invoke(String email){
        DI.profileRepository.sendResetPasswordEmail(email);
    }

}
