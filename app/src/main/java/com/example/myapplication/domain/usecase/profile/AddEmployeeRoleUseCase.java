package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class AddEmployeeRoleUseCase {
    public Completable invoke(String companyId, String companyName){
        return DI.profileRepository.addEmployeeRole(companyId, companyName);
    }
}
