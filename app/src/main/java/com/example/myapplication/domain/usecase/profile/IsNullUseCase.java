package com.example.myapplication.domain.usecase.profile;

import static com.example.myapplication.di.DI.service;

public class IsNullUseCase {
    public boolean invoke(){
        return service.auth.getCurrentUser() == null;
    }
}
