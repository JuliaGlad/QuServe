package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Single;

public class IsEmployeeUseCase {
    public Single<Boolean> isEmployee(){
        return DI.profileRepository.isEmployee();
    }
}
