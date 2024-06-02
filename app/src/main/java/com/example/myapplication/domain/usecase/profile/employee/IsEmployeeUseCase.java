package com.example.myapplication.domain.usecase.profile.employee;

import com.example.myapplication.di.profile.ProfileEmployeeDI;

import io.reactivex.rxjava3.core.Single;

public class IsEmployeeUseCase {
    public Single<Boolean> isEmployee(){
        return ProfileEmployeeDI.profileEmployeeRepository.isEmployee();
    }
}
