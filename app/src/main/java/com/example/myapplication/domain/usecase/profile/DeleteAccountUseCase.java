package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

public class DeleteAccountUseCase {
    public void invoke(){
        DI.profileRepository.deleteAccount();
    }
}
