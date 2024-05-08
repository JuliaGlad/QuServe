package com.example.myapplication.domain.usecase.profile;

import static com.example.myapplication.di.DI.service;

public class CheckAuthenticationUseCase {
    public boolean invoke(){
        return service.auth.getCurrentUser() != null && !service.auth.getCurrentUser().isAnonymous();
    }
}
