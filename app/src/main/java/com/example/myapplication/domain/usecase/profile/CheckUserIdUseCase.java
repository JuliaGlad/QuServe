package com.example.myapplication.domain.usecase.profile;

import static com.example.myapplication.di.DI.service;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.QueueDI;

public class CheckUserIdUseCase {
    public boolean invoke(){
        return service.auth.getCurrentUser() == null;
    }
}
